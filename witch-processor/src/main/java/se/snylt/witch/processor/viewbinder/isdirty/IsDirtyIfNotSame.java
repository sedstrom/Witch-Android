package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.utils.TypeUtils;

public class IsDirtyIfNotSame extends IsDirty {

    public IsDirtyIfNotSame(TypeName targetTypeName) {
        super(targetTypeName);
    }

    @Override
    MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder) {
        return builder.addStatement("return $N",
                TypeUtils.DIFF_VALUE.toString() + ".notSame(getValue(target), historyValue)");
    }
}
