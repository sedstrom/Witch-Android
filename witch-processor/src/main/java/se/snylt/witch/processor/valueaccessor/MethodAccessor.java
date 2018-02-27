package se.snylt.witch.processor.valueaccessor;

public class MethodAccessor extends DataAccessor {

    public MethodAccessor(String propertyAccessorName) {
        super(propertyAccessorName);
    }

    @Override
    public String accessPropertyString() {
        return propertyAccessorName + "()";
    }

    @Override
    public String viewHolderFieldName() {
        return propertyAccessorName + "_M";
    }
}
