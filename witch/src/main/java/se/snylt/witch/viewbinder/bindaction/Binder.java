package se.snylt.witch.viewbinder.bindaction;

import java.util.LinkedList;

/**
 * Bind a generic value to a generic target through one ore more chained {@link OnBind} actions.
 *
 * @param <Target> target to be bound with value.
 * @param <Value>  value to be bound to target.
 */
public class Binder<Target, Value> {

    /**
     * List of bind actions
     */
    private final LinkedList<OnBind<Target, Value>> onBinds;

    private Binder(OnBind<Target, Value> ...onBind) {
        this();
        onBinds.add(parallelMerge(onBind));
    }

    private Binder() {
        this.onBinds = new LinkedList<>();
    }

    private Binder(LinkedList<OnBind<Target, Value>> onBinds) {
        this.onBinds = onBinds;
    }

    /**
     * Creates a new {@link Binder} with provided {@param onBindHead} as only bind action in chain.
     *
     * @param onBind   initial bind action
     * @param <Target> target type to be bound with value.
     * @param <Value>  value type to be bound to target.
     * @return Binder with provided bind action.
     */
    @SafeVarargs
    public static <Target, Value> Binder<Target, Value> create(OnBind<Target, Value> ...onBind) {
        return new Binder<>(onBind);
    }

    public static <Target, Value> Binder<Target, Value> create(LinkedList<OnBind<Target, Value>> onBinds) {
        return new Binder<>(onBinds);
    }

    public static <Target, Value> Binder<Target, Value> create() {
        return new Binder<>();
    }

    /**
     * Bind {@param value} to {@param target}.
     *
     * @param target target to be bound with value.
     * @param value  value to be bound to target.
     */
    public void bind(Target target, Value value) {
        if(!onBinds.isEmpty()) {
            doBind(target, value, onBinds.getFirst());
        }
    }

    /**
     * Recursively run chain of bind actions.
     *
     * @param target binder target
     * @param value  binder value
     * @param node current bind action node to run
     */
    private void doBind(final Target target, final Value value, final OnBind<Target, Value> node) {
        node.bind(target, value, new OnBindListener() {
            @Override
            public void onBindDone() {
                Binder.this.onBindDone(target, value, node);
            }
        });
    }

    private void onBindDone(Target target, Value value, OnBind<Target, Value> node) {
        int next = onBinds.indexOf(node) + 1;
        if(next < onBinds.size()) {
            doBind(target, value, onBinds.get(next));
        }
    }

    /**
     * Add one or many {@link OnBind} actions to chain of bind actions.
     * Next node in chain won't run until all actions in {@param onBindNext} are done.
     *
     * @param onBindNext bind actions to run next in chain.
     * @return new {@link Binder} with bind action added last in chain.
     */
    @SafeVarargs
    public final Binder<Target, Value> next(OnBind<Target, Value> ...onBindNext) {
        LinkedList<OnBind<Target, Value>> nextList = new LinkedList<>(onBinds);
        nextList.addLast(parallelMerge(onBindNext));
        return Binder.create(nextList);
    }

    /**
     * Put bind actions in {@link ParallelOnBind} if > 1.
     * @param onBinds one or more {@link OnBind}
     * @return bind action that runs parallel bind actions
     */
    private OnBind<Target, Value> parallelMerge(OnBind<Target, Value>[] onBinds) {
        if(onBinds.length == 1) {
            return onBinds[0];
        }

        return new ParallelOnBind<>(onBinds);
    }
}
