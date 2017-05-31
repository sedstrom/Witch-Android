package se.snylt.witch.viewbinder;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class ViewBinder {

    public final int viewId;

    public ViewBinder(int viewId) {
        this.viewId = viewId;
    }

    public boolean bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        if (isDirty(target) || isAlwaysBind()) {
            View view = findView(viewHolder, viewFinder);
            getBinder(target).bind(view, getValue(target));
            return true;
        }
        return false;
    }

    /**
     * Find view for binding. First looks in view holder. If not foudn in view holder, view is looked up with viewfinder and
     * stored in view holder.
     * @param viewHolder view holder for storing view
     * @param viewFinder view fidner for view lookup
     * @return view for binding
     */
    private View findView(Object viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            View view = viewFinder.findViewById(viewId);
            setView(viewHolder, view);
            return view;
        }
        return (View) getView(viewHolder);
    }

    /**
     * Get value from target.
     * @param target target containing value to be bound.
     * @return the value
     */
    public abstract Object getValue(Object target);

    /**
     * Store view in view holder.
     * @param viewHolder view holder to store view in
     * @param view view to store
     */
    public abstract void setView(Object viewHolder, Object view);

    /**
     * Get view for binding.
     * @param viewHolder view holder that stores view after lookup.
     * @return the view
     */
    public abstract Object getView(Object viewHolder);

    /**
     * Check if value has been updated
     * @param target containing value
     * @return true if value is dirty and should be bound, otherwise false.
     */
    public abstract boolean isDirty(Object target);

    /**
     * Check if binging should always bind and skip value diff.
     * @return true if binding should always bind, otherwise false.
     */
    public abstract boolean isAlwaysBind();

    /**
     * Get binder for this view binder.
     *
     * @param target that contains value
     * @return binder
     */
    public abstract Binder getBinder(Object target);
}
