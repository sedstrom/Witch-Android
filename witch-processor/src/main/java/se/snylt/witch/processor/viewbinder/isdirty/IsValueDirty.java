package se.snylt.witch.processor.viewbinder.isdirty;


import com.squareup.javapoet.TypeName;

import se.snylt.witch.processor.dataaccessor.DataAccessor;

public class IsValueDirty extends IsDirty {

    private final DataAccessor valueAccessor;

    public IsValueDirty(TypeName targetTypeName, DataAccessor valueAccessor) {
        super(targetTypeName);
        this.valueAccessor = valueAccessor;
    }

    @Override
    String getIsDirty() {
        return String.format("target.%s.isDirty()",  valueAccessor.accessPropertyString());
    }
}
