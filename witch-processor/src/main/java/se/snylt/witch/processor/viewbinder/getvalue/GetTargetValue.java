package se.snylt.witch.processor.viewbinder.getvalue;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;
import se.snylt.witch.processor.viewbinder.MethodSpecModule;

public class GetTargetValue implements GetValue {

    private final TypeName targetTypeName;

    private final PropertyAccessor valueAccessor;

    private final TypeName valueTypeName;

    public GetTargetValue(TypeName targetTypeName, PropertyAccessor valueAccessor, TypeName valueTypeName) {
        this.targetTypeName = targetTypeName;
        this.valueAccessor = valueAccessor;
        this.valueTypeName = valueTypeName;
    }

    @Override
    public MethodSpec create() {
        return  MethodSpec.methodBuilder("getValue")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(targetTypeName, "target")
                .returns(valueTypeName)
                .addStatement("return target.$N", valueAccessor.accessPropertyString())
                .build();
    }

    @Override
    public TypeName getValueTypeName() {
        return valueTypeName;
    }

    @Override
    public PropertyAccessor getValueAccessor() {
        return valueAccessor;
    }

    @Override
    public String describeValue() {
        return "target." + valueAccessor.accessPropertyString();
    }
}
