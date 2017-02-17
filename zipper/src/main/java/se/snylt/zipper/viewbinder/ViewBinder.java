package se.snylt.zipper.viewbinder;

import android.util.Log;
import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

import static se.snylt.zipper.viewbinder.ZipperCore.TAG;

public abstract class ViewBinder {

    public final int viewId;

    public final String key;

    public final BindActions bindActions;

    private Object historyValue;

    private Object historyTarget;

    public ViewBinder(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = new BindActions(bindActions);
    }

    public void doBind(View view, Object value, Object ...mods) {
        BindActions bindActions = createModsBindActions(mods);
        if(bindActions != null) {
            bindActions.addAll(this.bindActions);
        } else {
            bindActions = this.bindActions;
        }

        bindActions.doBind(view, value);
    }

    private BindActions createModsBindActions(Object[] mods) {
        if(mods != null && mods.length > 0) {
            BindActions bindActionsAndMods = new BindActions();
            for(Object mod : mods) {
                List<BindAction> modActions = getModActions(mod);
                if(modActions != null) {
                    bindActionsAndMods.addAll(modActions);
                }
            }
            return bindActionsAndMods;
        }
        return null;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target, Object ...mods) {
        Object value = getValue(target);
        boolean isSameTarget = isSameTarget(target, historyTarget);
        if((isSameTarget && isNewValue(value, historyValue)) || !isSameTarget) {
            historyValue = value;
            historyTarget = target;
            doBind(findView(viewHolder, viewFinder), value, mods);
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

    public abstract List<BindAction> getModActions(Object mod);

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
