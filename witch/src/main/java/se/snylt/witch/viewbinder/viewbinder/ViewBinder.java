package se.snylt.witch.viewbinder.viewbinder;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class ViewBinder<Target, V extends View, Value, ViewHolder> {

    public final int viewId;

    protected Object historyValue = DiffValue.NO_HISTORY;

    public ViewBinder(int viewId) {
        this.viewId = viewId;
    }

    public boolean bind(ViewHolder viewHolder, ViewFinder viewFinder, Target target) {
        if (isDirty(target)) {
            V view = findView(viewHolder, viewFinder);
            historyValue = getValue(target);
            getBinder(target).bind(view, (Value) historyValue);
            return true;
        }
        return false;
    }

    /**
     * Find view for binding. First looks in view holder. If not found in view holder, view is looked up with viewfinder and
     * stored in view holder.
     * @param viewHolder view holder for storing view
     * @param viewFinder view finder for view lookup
     * @return view for binding
     */
    private V findView(ViewHolder viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            V view = (V) viewFinder.findViewById(viewId);
            setView(viewHolder, view);
            return view;
        }
        return getView(viewHolder);
    }

    /**
     * Get value from target.
     * @param target target containing value to be bound.
     * @return the value
     */
    public abstract Value getValue(Target target);

    /**
     * Store view in view holder.
     * @param viewHolder view holder to store view in
     * @param view view to store
     */
    public abstract void setView(ViewHolder viewHolder, V view);

    /**
     * Get view for binding.
     * @param viewHolder view holder that stores view after lookup.
     * @return the view
     */
    public abstract V getView(ViewHolder viewHolder);

    /**
     * Check if value has been updated
     * @param target containing value
     * @return true if value is dirty and should be bound, otherwise false.
     */
    public abstract boolean isDirty(Target target);

    /**
     * Get binder for this view binder.
     *
     * @param target that contains value
     * @return binder
     */
    public abstract Binder<V, Value> getBinder(Target target);
}
