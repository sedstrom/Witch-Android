package se.snylt.zipper.viewbinder;

import java.util.List;

import se.snylt.zipper.BindingSpec;

public class Binding {

    private final List<BindingSpec> bindingSpecs;

    private final OnUnbindListener onUnbindListener;

    public Binding(List<BindingSpec> bindingSpecs, OnUnbindListener onUnbindListener) {
        this.bindingSpecs = bindingSpecs;
        this.onUnbindListener = onUnbindListener;
    }

    public List<BindingSpec> getBindingSpecs() {
        return bindingSpecs;
    }

    public void unBind() {
        onUnbindListener.onUnbind();
    }
}
