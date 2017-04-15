package se.snylt.witch.viewbinder;

import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

class WitchCore {

    final static String TAG = "witch";

    private final ViewHolderFactory viewHolderFactory;

    private final BinderFactory binderFactory;

    public WitchCore(ViewHolderFactory viewHolderFactory, BinderFactory binderFactory) {
        this.viewHolderFactory = viewHolderFactory;
        this.binderFactory = binderFactory;
    }

    void doBind(Object target, ViewFinder viewFinder) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        TargetViewBinder targetViewBinder = getOrCreateBinder(target, viewFinder);
        targetViewBinder.bind(viewHolder, viewFinder, target);
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
