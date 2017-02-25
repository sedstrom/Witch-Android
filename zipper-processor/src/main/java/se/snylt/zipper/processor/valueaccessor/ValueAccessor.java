package se.snylt.zipper.processor.valueaccessor;

public abstract class ValueAccessor {

    protected final String valueAccessorName;

    protected ValueAccessor(String valueAccessorName) {
        this.valueAccessorName = valueAccessorName;
    }

    public abstract String accessValueString();

    public abstract String viewHolderFieldName();

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ValueAccessor) {
            return ((ValueAccessor) obj).valueAccessorName.equals(valueAccessorName);
        }

        return super.equals(obj);
    }
}
