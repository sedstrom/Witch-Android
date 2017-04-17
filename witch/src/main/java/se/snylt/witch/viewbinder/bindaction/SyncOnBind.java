package se.snylt.witch.viewbinder.bindaction;

public abstract class SyncOnBind<Target, Value> implements OnBind<Target, Value> {

    public abstract void onBind(Target target, Value value);

    @Override
    public void bind(Target target, Value value, OnBindListener onBindListener) {
        onBind(target, value);
        onBindListener.onBindDone();
    }
}

