package se.snylt.witch.processor.viewbinder.isdirty;

import com.squareup.javapoet.TypeName;

public class IsDirtyAlways extends IsDirty {

    public IsDirtyAlways(TypeName targetTypeName) {
        super(targetTypeName);
    }

    @Override
    String getIsDirty() {
        return "true";
    }
}
