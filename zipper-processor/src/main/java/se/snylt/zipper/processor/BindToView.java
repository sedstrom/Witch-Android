package se.snylt.zipper.processor;

import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Element;

public class BindToView {

    public final Integer viewId;

    public final Element value;

    public final List<BindActionDef> bindActions;

    public BindToView(Integer viewId, Element value) {
        this.viewId = viewId;
        this.value = value;
        this.bindActions = new LinkedList<>();
    }

    public void addBindAction(BindActionDef actionClassName){
        bindActions.add(actionClassName);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof BindToView) {
            return ((BindToView) obj).value.equals(value);
        }
        if(obj instanceof Element) {
            return obj.equals(value);
        }
        return super.equals(obj);
    }
}
