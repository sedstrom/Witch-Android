package se.snylt.witch.processor.binding;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.processor.valueaccessor.ValueAccessor;
import se.snylt.witch.processor.viewbinder.ViewBinder;
import se.snylt.witch.processor.viewbinder.isdirty.IsDirty;

public class ViewBindingDef {

    private final List<OnBindDef> onBinds;

    private final ViewBinder viewBinder;

    public ViewBindingDef(ViewBinder viewBinder) {
        this.viewBinder = viewBinder;
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
            return ((ViewBindingDef) obj).getValue().equals(getValue());
        }
        if(obj instanceof ValueAccessor) {
            return obj.equals(getValue());
        }
        return super.equals(obj);
    }

    public void setIsDirty(IsDirty isDirty) {
        viewBinder.isDirty = isDirty;
    }

    ValueAccessor getValue(){
        return viewBinder.valueAccessor;
    }

    public ViewBinder getViewBinder() {
        return viewBinder;
    }
}


