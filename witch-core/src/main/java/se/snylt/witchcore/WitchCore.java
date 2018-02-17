package se.snylt.witchcore;

import se.snylt.witchcore.viewfinder.ViewFinder;

public class WitchCore {

    private boolean loggingEnabled = false;

    private final Logger logger;

    private final ViewHolderFactory viewHolderFactory;

    private final BinderFactory binderFactory;

    public WitchCore(ViewHolderFactory viewHolderFactory, BinderFactory binderFactory, Logger logger) {
        this.viewHolderFactory = viewHolderFactory;
        this.binderFactory = binderFactory;
        this.logger = logger;
    }

    public boolean isLoggingEnabled() {
        return loggingEnabled;
    }

    public void setLoggingEnabled(boolean loggingEnabled) {
        this.loggingEnabled = loggingEnabled;
    }

    public void doBind(Object target, ViewFinder viewFinder) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        TargetViewBinder targetViewBinder = getOrCreateBinder(target, viewFinder);
        targetViewBinder.bind(viewHolder, viewFinder, target);

        if (loggingEnabled && logger != null) {
            String description = targetViewBinder.describeTarget(target);
            logger.v(description);
        }
    }

    private Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object key = keyForTarget(target);
        Object viewHolder = viewFinder.getViewHolder(key);
        if (viewHolder == null) {
            viewHolder = viewHolderFactory.createViewHolder(target);
            viewFinder.putViewHolder(key, viewHolder);
        }
        return viewHolder;
    }

    private TargetViewBinder getOrCreateBinder(Object target, ViewFinder viewFinder) {
        Object key = keyForTarget(target);
        TargetViewBinder targetViewBinder = viewFinder.getBinder(key);
        if (targetViewBinder == null) {
            targetViewBinder = binderFactory.createBinder(target);
            viewFinder.putBinder(key, targetViewBinder);
        }
        return targetViewBinder;
    }

    private Object keyForTarget(Object target){
        return target.getClass();
    }
}
