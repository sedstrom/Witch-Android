package se.snylt.witch.viewbinder.bindaction;

public abstract class OnBind<Target, Value> {

    /**
     * Next bind action in chain.
     */
    private OnBind<Target, Value> nextOnBind;

    /**
     * Called by {@link Binder} when {@param value} is about to be bound to {@param target}.
     *
     * @param target
     * @param value
     * @param onBindListener
     */
    public abstract void bind(Target target, Value value, OnBindListener onBindListener);

    /**
     * Get next bind action.
     *
     * @return next bind action or null.
     */
    public OnBind<Target, Value> getNextOnBind() {
        return nextOnBind;
    }

    /**
     * Set next bind action to to run after this.
     *
     * @param nextOnBind next bind action to run in bind sequence.
     */
    public void setNextOnBind(OnBind<Target, Value> nextOnBind) {
        if(this.nextOnBind != null) {
            this.nextOnBind.setNextOnBind(nextOnBind);
        } else {
            this.nextOnBind = nextOnBind;
        }
    }
}