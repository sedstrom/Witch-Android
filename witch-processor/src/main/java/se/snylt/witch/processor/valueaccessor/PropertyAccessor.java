package se.snylt.witch.processor.valueaccessor;

public abstract class PropertyAccessor {

    final String propertyAccessorName;

    PropertyAccessor(String propertyAccessorName) {
        this.propertyAccessorName = propertyAccessorName;
    }

    public abstract String accessPropertyString();

    public abstract String viewHolderFieldName();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof PropertyAccessor) {
            return ((PropertyAccessor) obj).propertyAccessorName.equals(propertyAccessorName);
        }

        return super.equals(obj);
    }
}
