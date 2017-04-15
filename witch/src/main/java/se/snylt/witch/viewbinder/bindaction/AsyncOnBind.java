package se.snylt.witch.viewbinder.bindaction;

public abstract class AsyncOnBind<Target, Value> extends OnBind<Target, Value> {

    public abstract void onBind(Target target, Value value, OnBindListener onBindListener);

    @Override
    public void bind(Target target, Value value, OnBindListener onBindListener) {
        onBind(target, value, onBindListener);
    }
}

