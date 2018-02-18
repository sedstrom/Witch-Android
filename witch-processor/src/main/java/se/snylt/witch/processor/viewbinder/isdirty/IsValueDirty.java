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
    String getIsDirty() {
        return String.format("target.%s.isDirty()",  valueAccessor.accessPropertyString());
    }
}
