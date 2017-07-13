package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

public class IsValueDirty extends IsDirty {

    private final PropertyAccessor valueAccessor;

    public IsValueDirty(TypeName targetTypeName, PropertyAccessor valueAccessor) {
        super(targetTypeName);
        this.valueAccessor = valueAccessor;
    }

    @Override
    MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder) {
        return builder.addStatement("return target.$N.isDirty()", valueAccessor.accessPropertyString());
    }
}
