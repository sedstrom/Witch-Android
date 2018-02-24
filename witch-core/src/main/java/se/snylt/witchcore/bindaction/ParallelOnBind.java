package se.snylt.witchcore.bindaction;


import java.util.concurrent.atomic.AtomicInteger;

/**
 * Runs a list of {@link OnBind} in parallel and callbacks {@link OnBindListener#onBindDone()} when
 * all bind actions are done.
 */
class ParallelOnBind<Target, Value> implements OnBind<Target, Value> {

    private final OnBind<Target, Value>[] onBinds;

    ParallelOnBind(OnBind<Target, Value>[] onBinds) {
        this.onBinds = onBinds;
    }

    @Override
    public void bind(Target target, Value value, OnBindListener onBindListener) {
        ParallelOnBindListener listener = new ParallelOnBindListener(onBinds.length, onBindListener);
        for (OnBind<Target, Value> onBind : onBinds) {
            onBind.bind(target, value, listener);
        }
    }

    /**
     * Callbacks {@link OnBindListener#onBindDone()} on {@param onBindListener} after {@param count} number of callbacks.
     */
    private static class ParallelOnBindListener implements OnBindListener {

        private final OnBindListener onBindListener;

        private final AtomicInteger count;

        ParallelOnBindListener(int count, OnBindListener onBindListener) {
            this.count = new AtomicInteger(count);
            this.onBindListener = onBindListener;
        }

        @Override
        public void onBindDone() {
            if (count.decrementAndGet() == 0) {
                this.onBindListener.onBindDone();
            }
        }
    }
}
