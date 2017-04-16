package se.snylt.witch.viewbinder;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class ViewBinder {

    public final int viewId;

    public final Binder binder;

    private final static Object NO_HISTORY = new Object();

    private Object historyValue = NO_HISTORY;

    public ViewBinder(int viewId, Binder binder) {
        this.viewId = viewId;
        this.binder = binder;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        Object value = getValue(target);
        if(isAlwaysBind() || isNewValue(value, historyValue)) {
            historyValue = value;
            View view = findView(viewHolder, viewFinder);
            // TODO check types
            binder.bind(view, value);
        }
    }

    private static boolean isNewValue(Object newValue, Object oldValue) {
        if(oldValue == NO_HISTORY) {
            return true;
        } else if(newValue == null && oldValue != null) {
            return true;
        } else if(newValue != null && oldValue == null) {
            return true;
        } else if(newValue == null) {
            return false;
        } else {
            return !newValue.equals(oldValue);
        }
    }

    private View findView(Object viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            View view = viewFinder.findViewById(viewId);
            setView(viewHolder, view);
            return view;
        }
        return (View) getView(viewHolder);
    }

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);

    public abstract boolean isAlwaysBind();
}
