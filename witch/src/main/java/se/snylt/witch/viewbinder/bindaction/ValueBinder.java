package se.snylt.witch.viewbinder.bindaction;

/**
 * Container for a {@link Binder} and a {@param Value}.
 *
 * @param <Target> target to be bound to.
 * @param <Value> value to be bound.
 */
public class ValueBinder<Target, Value> {

    private final Binder<Target, Value> binder;

    private Value value;

    private ValueBinder(Value value, Binder<Target, Value> binder) {
        this.binder = binder;
        this.value = value;
    }

    public static <Target, Value> ValueBinder<Target, Value> create(Value value, Binder<Target, Value> binder) {
        return new ValueBinder<>(value, binder);
    }

    public static <Target, Value> ValueBinder<Target, Value> create(Binder<Target, Value> binder) {
        return create(null, binder);
    }

    /**
     * Get stored value
     * @return stored value
     */
    public Value getValue() {
        return value;
    }

    /**
     * Set stored value
     * @param value value to be stored
     */
    public void setValue(Value value) {
        this.value = value;
    }

    /**
     * Get binder for binding value to target
     * @return binder that binds value to target.
     */
    public Binder<Target, Value> getBinder() {
        return binder;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof ValueBinder) {
            Object value = ((ValueBinder) obj).getValue();

            if(value != null && this.value == null) {
                return false;
            }

            if(value == null && this.value != null) {
                return false;
            }

            if(value == null) {
                return true;
            }

            return value.equals(this.value);
        }

        return super.equals(obj);

    }
}
