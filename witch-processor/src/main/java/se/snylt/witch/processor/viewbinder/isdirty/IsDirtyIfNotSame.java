package se.snylt.witch.processor.viewbinder.isdirty;


import se.snylt.witch.processor.TypeUtils;

public class IsDirtyIfNotSame implements IsDirty {

    @Override
    public String isDirty() {
        return TypeUtils.DIFF_VALUE.toString() + ".notSame(getValue(target), historyValue)";
    }
}
