package se.snylt.witch.processor.viewbinder.getvalue;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetTargetValue implements MethodSpecModule {

    private final TypeName targetTypeName;

    private final PropertyAccessor valueAccessor;

    public GetTargetValue(TypeName targetTypeName, PropertyAccessor valueAccessor) {
        this.targetTypeName = targetTypeName;
        this.valueAccessor = valueAccessor;
    }

    @Override
    public MethodSpec create() {
        return  MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Object.class, "target")
                .returns(Object.class)
                .addStatement("return (($T)target).$N", targetTypeName, valueAccessor.accessPropertyString())
                .build();
    }
}
