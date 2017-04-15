package se.snylt.witch.viewbinder.bindaction;

public abstract class SyncOnBind<Target, Value> extends OnBind<Target, Value> {

    public abstract void onBind(Target target, Value value);

    @Override
    public void bind(Target target, Value value, OnBindListener onBindListener) {
        onBind(target, value);
        onBindListener.onBindDone();
    }
}

