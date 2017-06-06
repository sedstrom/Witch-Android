package se.snylt.witch.viewbinder;

/**
 * Container for value to bind
 */
public class Value<T> {

    protected T value;

    /**
     * Flag indicating if value has been set since {@link #take()} has been called.
     */
    private boolean isDirty = true;

    public Value(T value) {
        this.value = value;
    }

    public Value() {}

    /**
     * Sets value. This will flag value as dirty and will be considered up for bind
     *
     * @param value value to set
     */
    public void set(T value) {
        this.value = value;
        this.isDirty = true;
    }

    /**
     * Get value
     *
     * @return value
     */
    public T get() {
        return value;
    }

    /**
     * Get value and clear dirty flag.
     *
     * @return value
     */
    public T take() {
        this.isDirty = false;
        return get();
    }

    /**
     * Retrieve the value and mark as dirty. In this way, value does not have to be set in order to be bound again.
     */
    public T update() {
        isDirty = true;
        return get();
    }


    /**
     * Check if value has changed since last call to {@link #take()}
     */
    public boolean isDirty() {
        return isDirty;
    }
}
