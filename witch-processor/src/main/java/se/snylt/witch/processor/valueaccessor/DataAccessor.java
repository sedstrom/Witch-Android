package se.snylt.witch.processor.valueaccessor;

public abstract class DataAccessor {

    final String propertyAccessorName;

    DataAccessor(String propertyAccessorName) {
        this.propertyAccessorName = propertyAccessorName;
    }

    public abstract String accessPropertyString();

    public abstract String viewHolderFieldName();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof DataAccessor) {
            return ((DataAccessor) obj).propertyAccessorName.equals(propertyAccessorName);
        }

        return super.equals(obj);
    }
}
