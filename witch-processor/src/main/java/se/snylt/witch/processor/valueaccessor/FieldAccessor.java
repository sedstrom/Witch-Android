package se.snylt.witch.processor.valueaccessor;

public class FieldAccessor extends PropertyAccessor {

    public FieldAccessor(String propertyAccessorName) {
        super(propertyAccessorName);
    }

    @Override
    public String accessPropertyString() {
        return propertyAccessorName;
    }

    @Override
    public String viewHolderFieldName() {
        return propertyAccessorName;
    }
}
