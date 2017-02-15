package se.snylt.zipper.viewbinder;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

class ZipperCore {

    final static String TAG = "Zipper";

    private final static HashMap<Object, WeakReference<Binder>> binders = new HashMap<>();

    private final ViewHolderFactory viewHolderFactory;

    private final BinderFactory binderFactory;

    public ZipperCore(ViewHolderFactory viewHolderFactory, BinderFactory binderFactory) {
        this.viewHolderFactory = viewHolderFactory;
        this.binderFactory = binderFactory;
    }

    Binding doBind(Object target, ViewFinder viewFinder, Object ...mods) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        Binder binder = getOrCreateBinder(target);
        return binder.bind(viewHolder, viewFinder, target, mods);
    }

    private Binder getOrCreateBinder(Object target) {
        final Object key = getKeyForTarget(target);
        Binder binder;
        if (!isBinderCreated(key)) {
            binder = binderFactory.createBinder(target);
            binder.setOnBindingAbandonedListener(createBinderAbandonedListener(key));
            binders.put(key, new WeakReference(binder));
        } else {
            binder = binders.get(key).get();
        }
        return binder;
    }

    private boolean isBinderCreated(Object key) {
        return binders.containsKey(key) && binders.get(key).get() != null;
    }

    private Object getKeyForTarget(Object target) {
        return target.getClass();
    }

    private static BindingAbandonedListener createBinderAbandonedListener(final Object key) {
        return new BindingAbandonedListener() {
            @Override
            public void onBindingAbandoned() {
                binders.remove(key);
            }
        };
    }

    private Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if (viewHolder == null) {
            viewHolder = viewHolderFactory.createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

}
