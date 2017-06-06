package se.snylt.witch.viewbinder;

import se.snylt.witch.viewbinder.bindaction.Binder;

public abstract class ValueViewBinder extends ViewBinder {

    public final Binder binder;

    public ValueViewBinder(int viewId, Binder binder) {
        super(viewId);
        this.binder = binder;
    }
}
