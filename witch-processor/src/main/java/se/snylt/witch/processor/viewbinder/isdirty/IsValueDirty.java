package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

import static se.snylt.witch.processor.TypeUtils.VALUE;

public class IsValueDirty extends IsDirty {

    private final TypeName targetTypeName;

    private final PropertyAccessor valueAccessor;

    public IsValueDirty(TypeName targetTypeName, PropertyAccessor valueAccessor) {
        this.targetTypeName = targetTypeName;
        this.valueAccessor = valueAccessor;
    }

    @Override
    MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder) {
        return builder.addStatement("return (($T)(($T)target).$N).isDirty()",
                VALUE, targetTypeName, valueAccessor.accessPropertyString());
    }
}
