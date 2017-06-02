package se.snylt.witch.viewbinder;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

public abstract class ValueBinderViewBinder extends DefaultViewBinder {

    public ValueBinderViewBinder(int viewId) {
        super(viewId, null);
    }

    public abstract ValueBinder getValueBinder(Object target);

    @Override
    public Object getValue(Object target) {
        return getValueBinder(target).take();
    }

    @Override
    public Binder getBinder(Object target) {
        return getValueBinder(target).getBinder();
    }
}
