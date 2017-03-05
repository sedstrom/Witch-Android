package se.snylt.witch.processor.valueaccessor;

public class MethodAccessor extends ValueAccessor {

    public MethodAccessor(String valueAccessorName) {
        super(valueAccessorName);
    }

    @Override
    public String accessValueString() {
        return valueAccessorName + "()";
    }

    @Override
    public String viewHolderFieldName() {
        return valueAccessorName + "_M";
    }
}
