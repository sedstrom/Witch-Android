package se.snylt.witch.viewbinder.bindaction;

/**
 * Binds a generic value to a generic target with one ore more chained {@link OnBind} actions.
 *
 * @param <Target> target to be bound with value.
 * @param <Value> value to be bound to target.
 */
public class Binder<Target, Value> {

    /**
     * Starting point for chain of bind actions.
     */
    protected OnBind<Target, Value> onBind;

    protected Binder(OnBind<Target, Value> onBind) {
        this.onBind = onBind;
    }

    /**
     * Creates a new {@link Binder} with provided {@param onBind} as only bind action in chain.
     *
     * @param onBind initial bind action
     * @param <Target> target type to be bound with value.
     * @param <Value> value type to be bound to target.
     * @return Binder with provided bind action.
     */
    public static <Target, Value> Binder<Target, Value> create(OnBind<Target, Value> onBind) {
        return new Binder<>(onBind);
    }

    public static <Target, Value> Binder<Target, Value> create() {
        return new Binder<>(null);
    }

    /**
     * Bind {@param value} to {@param target}.
     *
     * @param target target to be bound with value.
     * @param value value to be bound to target.
     */
    public void bind(Target target, Value value) {
        // Start bind chain with onBind as root action.
        doBind(target, value, onBind);
    }

    /**
     * Recursively run chain of bind actions.
     * @param target binder target
     * @param value binder value
     * @param onBind current bind action to run
     */
    private void doBind(final Target target, final Value value, final OnBind<Target, Value> onBind) {
        if(onBind != null) {
            onBind.bind(target, value, new OnBindListener() {
                @Override
                public void onBindDone() {
                    // Run until setNextOnBind action is null.
                    doBind(target, value, onBind.getNextOnBind());
                }
            });
        }
    }

    /**
     * Add a {@link OnBind} action to chain of bind actions.
     *
     * @param onBind bind action to run next in chain.
     * @return new {@link Binder} with bind action added last in chain.
     */
    public Binder<Target, Value> next(OnBind<Target, Value> onBind) {
        if(this.onBind != null) {
            this.onBind.setNextOnBind(onBind);
        } else {
            this.onBind = onBind;
        }

        return Binder.create(this.onBind);
    }
}
