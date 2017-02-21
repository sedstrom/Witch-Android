package se.snylt.zipper.viewbinder;


import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

public class BindActions {

    public final List<OnPreBindAction> preBindActions = new ArrayList<>();

    public final List<OnBindAction> onBindActions = new ArrayList<>();

    public final List<OnPostBindAction> postBindActions = new ArrayList<>();

    public BindActions(List<BindAction> bindActions) {
        addAll(bindActions);
    }

    public BindActions(BindAction ...bindActions) {
        addAll(bindActions);
    }

    private void add(BindAction action) {

        if(action instanceof OnPreBindAction) {
            preBindActions.add((OnPreBindAction) action);
        }

        if(action instanceof OnBindAction) {
            onBindActions.add((OnBindAction) action);
        }

        if (action instanceof OnPostBindAction) {
            postBindActions.add((OnPostBindAction) action);
        }
    }

    public final void addAll(Collection<? extends BindAction> actions) {
        for(BindAction action: actions) {
            add(action);
        }
    }

    public final void addAll(BindAction[] actions) {
        for(BindAction action: actions) {
            add(action);
        }
    }

    public final void addAll(BindActions bindActions) {
        addAll(bindActions.preBindActions);
        addAll(bindActions.onBindActions);
        addAll(bindActions.postBindActions);
    }

    public void doBind(final View view, final Object value){

        AtomicPreBindDone preBind = new AtomicPreBindDone(new PreBindDone() {
            @Override
            public void done() {
                for(OnBindAction action: onBindActions) {
                    action.onBind(view, value);
                }

                for(OnPostBindAction action: postBindActions) {
                    action.onPostBind(view, value);
                }
            }
        });

        for(OnPreBindAction action: preBindActions) {
            preBind.waitForDone();
            action.onPreBind(view, value, preBind);
        }

        preBind.check();
    }
}
