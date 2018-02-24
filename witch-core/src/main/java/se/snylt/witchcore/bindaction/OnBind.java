package se.snylt.witchcore.bindaction;

public interface OnBind<Target, Value> {

    /**
     * Called by {@link Binder} when {@param value} is about to be bound to {@param target}.
     *
     * @param target
     * @param value
     * @param onBindListener
     */
    void bind(Target target, Value value, OnBindListener onBindListener);
}