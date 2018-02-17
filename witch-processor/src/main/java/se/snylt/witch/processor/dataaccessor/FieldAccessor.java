package se.snylt.witch.processor.dataaccessor;

public class FieldAccessor extends DataAccessor {

    public FieldAccessor(String propertyAccessorName) {
        super(propertyAccessorName);
    }

    @Override
    public String accessPropertyString() {
        return propertyAccessorName;
    }
}
