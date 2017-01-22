package se.snylt.zipper;

import java.util.List;

import se.snylt.zipper.BindAction;

public class BindingSpec {

    public final int viewId;

    public final String key;

    private final List<BindAction> bindActions;

    public BindingSpec(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = bindActions;
    }

    public List<BindAction> getBindActions() {
        return bindActions;
    }
}
