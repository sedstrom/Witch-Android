package se.snylt.witch.viewbinder.viewbinder;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class DefaultViewBinder extends ViewBinder {

    public final Binder binder;

    protected Object historyValue = DiffValue.NO_HISTORY;

    public DefaultViewBinder(int viewId, Binder binder) {
        super(viewId);
        this.binder = binder;
    }

    public boolean bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        if(super.bind(viewHolder, viewFinder, target)){
            historyValue = getValue(target);
            return true;
        }
        return false;
    }

    @Override
    public abstract boolean isDirty(Object target);

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
