package se.snylt.witch.processor.binding;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;

public class ViewBindingDef {

    public final Integer viewId;

    // Value
    public final ValueAccessor value;

    // Flag always bind
    private boolean alwaysBind = false;

    private final List<OnBindDef> onBinds;

    public ViewBindingDef(Integer viewId, ValueAccessor value) {
        this.viewId = viewId;
        this.value = value;
        this.onBinds = new ArrayList<>();
    }

    public void addOnBind(OnBindDef onBindDef) {
        onBinds.add(onBindDef);
    }

    public List<OnBindDef> getOnBinds(){
        return onBinds;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ViewBindingDef) {
            return ((ViewBindingDef) obj).value.equals(value);
        }
        if(obj instanceof ValueAccessor) {
            return obj.equals(value);
        }
        return super.equals(obj);
    }

    public boolean isAlwaysBind() {
        return alwaysBind;
    }

    public void setAlwaysBind(boolean alwaysBind) {
        this.alwaysBind = alwaysBind;
    }
}


