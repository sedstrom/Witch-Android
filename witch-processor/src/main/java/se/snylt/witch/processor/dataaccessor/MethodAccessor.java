package se.snylt.witch.processor.dataaccessor;

public class MethodAccessor extends DataAccessor {

    public MethodAccessor(String propertyAccessorName) {
        super(propertyAccessorName);
    }

    @Override
    public String accessPropertyString() {
        return propertyAccessorName + "()";
    }
}
