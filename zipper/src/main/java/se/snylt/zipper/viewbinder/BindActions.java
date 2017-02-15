package se.snylt.zipper.viewbinder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;

public class BindActions {

    public final List<OnPreBindAction> preBindActions = new ArrayList<>();

    public final List<OnBindAction> onBindActions = new ArrayList<>();

    public final List<OnPostBindAction> postBindActions = new ArrayList<>();

    public BindActions(List<BindAction> bindActions) {
        addAll(bindActions);
    }

    public BindActions() {}

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

    public final void addAll(BindActions bindActions) {
        addAll(bindActions.preBindActions);
        addAll(bindActions.onBindActions);
        addAll(bindActions.postBindActions);
    }

    public final void clear(){
        preBindActions.clear();
        onBindActions.clear();
        postBindActions.clear();
    }
}
