package se.snylt.witch.processor.viewbinder.getbinder;

import se.snylt.witch.processor.binding.OnBindDef;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class GetBinder implements MethodSpecModule {

    public abstract void addOnBind(OnBindDef onBindDef);

}
