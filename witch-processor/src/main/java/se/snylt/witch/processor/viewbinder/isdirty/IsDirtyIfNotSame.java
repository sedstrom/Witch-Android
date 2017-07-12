package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.MethodSpec;

import se.snylt.witch.processor.TypeUtils;

public class IsDirtyIfNotSame extends IsDirty {

    @Override
    MethodSpec.Builder addReturnStatement(MethodSpec.Builder builder) {
        return builder.addStatement("return $N",
                TypeUtils.DIFF_VALUE.toString() + ".notSame(getValue(target), historyValue)");
    }
}
