package se.snylt.zipper.processor.valueaccessor;

public class FieldAccessor extends ValueAccessor {

    public FieldAccessor(String valueAccessorName) {
        super(valueAccessorName);
    }

    @Override
    public String accessValueString() {
        return valueAccessorName;
    }

    @Override
    public String viewHolderFieldName() {
        return valueAccessorName;
    }
}
