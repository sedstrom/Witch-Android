package se.snylt.witch.processor.viewbinder.isdirty;


import se.snylt.witch.processor.TypeUtils;

public class IsDirtyIfNotEquals implements IsDirty {

    @Override
    public String isDirty() {
        return TypeUtils.DIFF_VALUE.toString() + ".notEquals(getValue(target), historyValue)";
    }
}
