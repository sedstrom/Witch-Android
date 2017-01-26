package se.snylt.zipper;

import java.util.List;

public abstract class BindingSpec {

    public final int viewId;

    public final String key; // Key for property value

    private final List<BindAction> bindActions;

    public BindingSpec(int viewId, String key, List<BindAction> bindActions) {
        this.viewId = viewId;
        this.key = key;
        this.bindActions = bindActions;
    }

    public List<BindAction> getBindActions() {
        return bindActions;
    }

    public abstract void setView(Object viewHolder, Object view);

    public abstract Object getView(Object viewHolder);
}
