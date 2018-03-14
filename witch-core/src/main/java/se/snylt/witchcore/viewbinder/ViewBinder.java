package se.snylt.witchcore.viewbinder;

import se.snylt.witchcore.viewfinder.ViewFinder;

public abstract class ViewBinder<Target, View, Value, ViewHolder> {

    private final int viewId;

    protected Object historyValue = DiffValue.NO_HISTORY;

    public ViewBinder(int viewId) {
        this.viewId = viewId;
    }

    public boolean bind(ViewHolder viewHolder, ViewFinder viewFinder, Target target) {
        if (isDirty(target)) {
            View view = findView(viewHolder, viewFinder);
            Value value = getValue(target);
            Value hv = historyValue == DiffValue.NO_HISTORY ? null : (Value) historyValue;
            historyValue = value;
            bind(target, view, value, hv);
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
    private View findView(ViewHolder viewHolder, ViewFinder viewFinder) {
        if (getView(viewHolder) == null) {
            View view = (View) viewFinder.findViewById(viewId);
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
    public abstract void setView(ViewHolder viewHolder, View view);

    /**
     * Get view for binding.
     * @param viewHolder view holder that stores view after lookup.
     * @return the view
     */
    public abstract View getView(ViewHolder viewHolder);

    /**
     * Check if value has been updated
     * @param target containing value
     * @return true if value is dirty and should be bound, otherwise false.
     */
    public abstract boolean isDirty(Target target);

    /**
     * Binds value to view and provides previously bound value
     * @param target target
     * @param view view to bind to
     * @param value value to bind
     * @param historyValue previously bound value
     */
    public abstract void bind(Target target, View view, Value value, Value historyValue);
}
