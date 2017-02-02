package se.snylt.zipper.processor.binding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.lang.model.element.Element;

public class ViewBindingDef {

    // View
    public final Integer viewId;

    // Value
    public final Element value;

    // Bind actions
    private final List<BindActionDef> onPreBindActions;
    private final List<BindActionDef> onBindActions;
    private final List<BindActionDef> onPostBindActions;

    public ViewBindingDef(Integer viewId, Element value) {
        this.viewId = viewId;
        this.value = value;
        this.onPreBindActions = new LinkedList<>();
        this.onBindActions = new LinkedList<>();
        this.onPostBindActions = new LinkedList<>();
    }

    public void addOnPreBindAction(BindActionDef onPostBindAction) {
        onPreBindActions.add(onPostBindAction);
    }

    public void addOnBindAction(BindActionDef onBindAction){
        onBindActions.add(onBindAction);
    }

    public void addOnPostBindAction(BindActionDef onPostBindAction) {
        onPostBindActions.add(onPostBindAction);
    }

    public List<BindActionDef> getBindActions(){
        List<BindActionDef> allActions = new ArrayList();
        allActions.addAll(onPreBindActions);
        allActions.addAll(onBindActions);
        allActions.addAll(onPostBindActions);
        return allActions;
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
