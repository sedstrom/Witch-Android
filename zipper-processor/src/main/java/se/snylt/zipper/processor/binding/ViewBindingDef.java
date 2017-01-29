package se.snylt.zipper.processor.binding;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Element;

public class ViewBindingDef {

    // View
    public final Integer viewId;

    // Value
    public final Element value;

    // Bind actions
    public final List<BindActionDef> bindActions;

    public ViewBindingDef(Integer viewId, Element value) {
        this.viewId = viewId;
        this.value = value;
        this.bindActions = new LinkedList<>();
    }

    public void addBindAction(BindActionDef actionClassName){
        bindActions.add(actionClassName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ViewBindingDef) {
            return ((ViewBindingDef) obj).value.equals(value);
        }
        if(obj instanceof Element) {
            return obj.equals(value);
        }
        return super.equals(obj);
    }
}
