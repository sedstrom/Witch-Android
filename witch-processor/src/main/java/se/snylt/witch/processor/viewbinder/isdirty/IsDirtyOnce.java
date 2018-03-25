package se.snylt.witch.processor.viewbinder.isdirty;

import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.utils.TypeUtils;

public class IsDirtyOnce extends IsDirty {

    public IsDirtyOnce(TypeName targetTypeName) {
        super(targetTypeName);
    }

    @Override
    String getIsDirty() {
        return "!" + TypeUtils.DIFF_UTILS.toString() + ".hasHistory(null, historyData)";
    }
}
