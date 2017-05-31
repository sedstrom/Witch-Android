package se.snylt.witch.viewbinder;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public abstract class DefaultViewBinder extends ViewBinder {

    public final Binder binder;

    private final static Object NO_HISTORY = new Object();

    private Object historyValue = NO_HISTORY;

    public DefaultViewBinder(int viewId, Binder binder) {
        super(viewId);
        this.binder = binder;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        super.bind(viewHolder, viewFinder, target);
        historyValue = getValue(target);
    }

    public Binder getBinder(Object value) {
        return binder;
    }

    /**
     * Check if {@param newValue} is different from {@param oldValue} and should be bound.
     * @param value candidate for bind
     * @return true if {@param newValue} should be bound, otherwise false
     */
    private boolean isNewValue(Object value) {
        if(historyValue == NO_HISTORY) {
            return true;
        } else if(value == null && historyValue != null) {
            return true;
        } else if(value != null && historyValue == null) {
            return true;
        } else if(value == null) {
            return false;
        } else {
            return !value.equals(historyValue);
        }
    }

    @Override
    public boolean isDirty(Object target) {
        return isNewValue(getValue(target));
    }

    public abstract Object getValue(Object target);

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
