package se.snylt.zipper.viewbinder;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

public class BindActionsRunner {

    static Object NO_HISTORY = new Object();

    private static BindActionsRunner INSTANCE;

    BindActionsRunner() {}

    // Test
    static void withRunner(BindActionsRunner runner){
        INSTANCE = runner;
    }

    public static BindActionsRunner runner() {
        if(INSTANCE == null) {
            INSTANCE = new BindActionsRunner();
        }
        return INSTANCE;
    }

    public void bind(final BindActions bindActions, final View view, final Object value) {

        AtomicPreBindDone preBind = new AtomicPreBindDone(new PreBindDone() {
            @Override
            public void done() {
                for (OnBindAction action : bindActions.onBindActions) {
                    action.onBind(view, value);
                }

                for (OnPostBindAction action : bindActions.onPostBindActions) {
                    action.onPostBind(view, value);
                }
            }
        });

        for (OnPreBindAction action : bindActions.onPreBindActions) {
            preBind.waitForDone();
            action.onPreBind(view, value, preBind);
        }

        preBind.check();
    }

    public boolean isNewValue(Object newValue, Object oldValue) {
        if(oldValue == NO_HISTORY) {
            return true;
        } else if(newValue == null && oldValue != null) {
            return true;
        } else if(newValue != null && oldValue == null) {
            return true;
        } else if(newValue == null && oldValue == null) {
            return false;
        } else {
            return !newValue.equals(oldValue);
        }
    }
}

