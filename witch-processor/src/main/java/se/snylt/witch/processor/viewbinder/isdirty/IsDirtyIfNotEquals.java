package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.utils.TypeUtils;

public class IsDirtyIfNotEquals extends IsDirty {

    public IsDirtyIfNotEquals(TypeName targetTypeName) {
        super(targetTypeName);
    }

    @Override
    String getIsDirty() {
        return TypeUtils.DIFF_VALUE.toString() + ".notEquals(getValue(target), historyValue)";
    }
}
