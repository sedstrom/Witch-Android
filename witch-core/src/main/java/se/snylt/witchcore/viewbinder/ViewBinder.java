package se.snylt.witchcore.viewbinder;

import se.snylt.witchcore.viewfinder.ViewFinder;

public abstract class ViewBinder<Target, View, Data, ViewHolder> {

    private final int viewId;

    protected Object historyData = DiffUtils.NO_HISTORY;

    public ViewBinder(int viewId) {
        this.viewId = viewId;
    }

    public boolean bind(ViewHolder viewHolder, ViewFinder viewFinder, Target target) {
        if (isDirty(target)) {
            View view = findView(viewHolder, viewFinder);
            Data data = getData(target);
            Data hv = historyData == DiffUtils.NO_HISTORY ? null : (Data) historyData;
            historyData = data;
            bind(target, view, data, hv);
            return true;
        }
        return false;
    }

    /**
     * Find view for binding. First looks in view holder.
     * If not found in view holder, view is looked up with viewfinder and stored in view holder.
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
     * Get data from target.
     * @param target target containing data to be bound.
     * @return the data
     */
    public abstract Data getData(Target target);

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
     * Check if data has is dirty and should be bound
     * @param target containing data
     * @return true if data is dirty and should be bound, otherwise false.
     */
    public abstract boolean isDirty(Target target);

    /**
     * Binds data to view and provides previously bound data
     * @param target target
     * @param view view to bind to
     * @param data data to bind
     * @param historyData previously bound data
     */
    public abstract void bind(Target target, View view, Data data, Data historyData);
}
