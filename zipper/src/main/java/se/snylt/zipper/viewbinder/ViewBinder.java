package se.snylt.zipper.viewbinder;

import android.util.Log;
import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

import static se.snylt.zipper.viewbinder.ZipperCore.TAG;

public abstract class ViewBinder {

    public final int viewId;

    public final String key;

    public final BindActions bindActions;

    private BindActions bindActionsAndMods;

    private Object historyValue;

    private Object historyTarget;

    public ViewBinder(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = new BindActions(bindActions);
    }

    public abstract List<BindAction> getModActions(Object mod);

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);

    public void doBind(View view, Object value, Object ...mods) {
        BindActions bindActions = prepareBindActions(mods);

        for(OnPreBindAction action: bindActions.preBindActions) {
            action.onPreBind(view, value);
        }

        for(OnBindAction action: bindActions.onBindActions) {
            action.onBind(view, value);
        }

        for(OnPostBindAction action: bindActions.postBindActions) {
            action.onPostBind(view, value);
        }

        destroyBindActions();
    }

    private void destroyBindActions() {
        if(bindActionsAndMods != null) {
            bindActionsAndMods.clear();
        }
    }

    private BindActions prepareBindActions(Object[] mods) {
        if(mods != null && mods.length > 0) {
            if(bindActionsAndMods == null) {
                bindActionsAndMods = new BindActions();
            }
            bindActionsAndMods.clear();
            bindActionsAndMods.addAll(this.bindActions);
            for(Object mod : mods) {
                List<BindAction> modActions = getModActions(mod);
                if(modActions != null) {
                    bindActionsAndMods.addAll(modActions);
                }
            }
            return bindActionsAndMods;
        } else {
            return this.bindActions;
        }
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target, Object ...mods) {
        Object value = getValue(target);
        boolean isSameTarget = isSameTarget(target, historyTarget);
        if((isSameTarget && isNewValue(value, historyValue)) || !isSameTarget) {
            View view = findView(viewHolder, viewFinder);
            doBind(view, value, mods);

            // For later
            historyTarget = target;
            historyValue = value;
        } else {
            Log.d(TAG, "Skip bind because of no change");
        }
    }

    private boolean isSameTarget(Object target, Object history) {
        return target == null && history == null ? false : target == history;
    }

    private boolean isNewValue(Object newValue, Object oldValue) {

        if(newValue == null && oldValue != null) {
            return true;
        } else if(newValue != null && oldValue == null) {
           return true;
        } else if(newValue == null && oldValue == null) {
            return false;
        } else {
            return !newValue.equals(oldValue);
        }
    }

    private View findView(Object viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            setView(viewHolder, viewFinder.findViewById(viewId));
        }
        return (View) getView(viewHolder);
    }
}
