package se.snylt.witch.processor.viewbinder.getbinder;

import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public abstract class GetBinder implements MethodSpecModule {

    public abstract void addOnBind(OnBind onBind);

}
