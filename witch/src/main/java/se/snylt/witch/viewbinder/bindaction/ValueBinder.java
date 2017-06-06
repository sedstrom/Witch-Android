package se.snylt.witch.viewbinder.bindaction;

import se.snylt.witch.viewbinder.Value;

/**
 * Container for a {@link Binder} and a {@param V}.
 *
 * @param <Target> target to be bound to.
 * @param <V> value to be bound.
 */
public class ValueBinder<Target, V> extends Value<V> {

    private final Binder<Target, V> binder;

    private ValueBinder(V value, Binder<Target, V> binder) {
        super(value);
        this.binder = binder;
    }

    public static <Target, Value> ValueBinder<Target, Value> create(Value value, Binder<Target, Value> binder) {
        return new ValueBinder<>(value, binder);
    }

    public static <Target, Value> ValueBinder<Target, Value> create(Binder<Target, Value> binder) {
        return create(null, binder);
    }

    /**
     * Get binder for binding value to target
     * @return binder that binds value to target.
     */
    public Binder<Target, V> getBinder() {
        return binder;
    }
}
