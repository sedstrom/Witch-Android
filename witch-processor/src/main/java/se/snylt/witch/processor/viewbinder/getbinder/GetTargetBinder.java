package se.snylt.witch.processor.viewbinder.getbinder;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.binding.OnBind;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

import static se.snylt.witch.processor.utils.TypeUtils.BINDER;

public class GetTargetBinder extends GetBinder {

    private final TypeName targetTypeName;

    private final PropertyAccessor binderAccessor;

    public GetTargetBinder(TypeName targetTypeName, PropertyAccessor binderAccessor) {
        this.targetTypeName = targetTypeName;
        this.binderAccessor = binderAccessor;
    }

    @Override
    public MethodSpec create() {
        return MethodSpec.methodBuilder("getBinder")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(BINDER)
                .addStatement("return target.$N", binderAccessor.accessPropertyString())
                .build();
    }

    @Override
    public void addOnBind(OnBind onBind) {
        throw new IllegalAccessError("Not supported");
    }
}
