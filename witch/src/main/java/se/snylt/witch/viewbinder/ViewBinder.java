package se.snylt.witch.viewbinder;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class ViewBinder {

    public final int viewId;

    public ViewBinder(int viewId) {
        this.viewId = viewId;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        if (isDirty(target) || isAlwaysBind()) {
            View view = findView(viewHolder, viewFinder);
            getBinder(target).bind(view, getValue(target));
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

    public abstract boolean isDirty(Object target);

    public abstract boolean isAlwaysBind();

    /**
     * Extract binder if {@param target} is a binder container.
     *
     * @param value that might have a binder
     * @return binder
     */
    public abstract Binder getBinder(Object target);
}
