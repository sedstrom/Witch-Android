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

    void doBind(Object target, ViewFinder viewFinder, Object ...mods) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        Binder binder = getOrCreateBinder(target, viewFinder);
        binder.bind(viewHolder, viewFinder, target, mods);
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

    private Binder getOrCreateBinder(Object target, ViewFinder viewFinder) {
        Object key = keyForTarget(target);
        Binder binder = viewFinder.getBinder(key);
        if (binder == null) {
            binder = binderFactory.createBinder(target);
            viewFinder.putBinder(key, binder);
        }
        return binder;
    }

    private Object keyForTarget(Object target){
        return target.getClass();
    }
}
