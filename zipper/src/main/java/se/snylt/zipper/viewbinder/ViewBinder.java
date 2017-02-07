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

    public final List<BindAction> bindActions;

    public ViewBinder(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = bindActions;
    }

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);

    public void doBind(View view, Object value) {
        for(BindAction action: bindActions) {
            if (action instanceof OnPreBindAction) {
                ((OnPreBindAction) action).onPreBind(view, value);
            }
            if (action instanceof OnBindAction) {
                ((OnBindAction) action).onBind(view, value);
            }
            if (action instanceof OnPostBindAction) {
                ((OnPostBindAction) action).onPostBind(view, value);
            }
        }
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target, Object history) {
        Object newValue = getValueIfNotNull(target);
        Object oldValue = getValueIfNotNull(history);
        boolean isSameTarget = isSameTarget(target, history);
        if((isSameTarget && isNewValue(newValue, oldValue)) || !isSameTarget) {
            View view = findView(viewHolder, viewFinder);
            doBind(view, newValue);
        } else {
            Log.d(TAG, "Skip bind because of no change");
        }
    }

    private boolean isSameTarget(Object target, Object history) {
        return target == null && history == null ? false : target == history;
    }

    private Object getValueIfNotNull(Object target) {
        return (target == null) ? null : getValue(target);
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
