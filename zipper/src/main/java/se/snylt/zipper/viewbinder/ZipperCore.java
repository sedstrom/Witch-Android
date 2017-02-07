package se.snylt.zipper.viewbinder;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public class ZipperCore {

    final static String TAG = "Zipper";

    private final static HashMap<Object, WeakReference<Binding>> bindings = new HashMap<>();

    private final ViewHolderFactory viewHolderFactory;

    private final BinderFactory binderFactory;

    public ZipperCore(ViewHolderFactory viewHolderFactory, BinderFactory binderFactory) {
        this.viewHolderFactory = viewHolderFactory;
        this.binderFactory = binderFactory;
    }

    UnBinder doBind(Object target, ViewFinder viewFinder) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        Binding binding = getOrCreateBinding(target);
        return binding.bind(viewHolder, viewFinder, target);
    }

    /**
     * Get binding for provided target view model. Creates new binding if not already existing.
     * @param target annotated view model
     * @return binding for target
     */
    private Binding getOrCreateBinding(Object target) {
        final Object key = getKeyForTarget(target);
        Binding binding;
        if (!bindingExists(key)) {
            binding = binderFactory.createBinding(target);
            binding.setOnBindingAbandonedListener(createBindingAbandonedListener(key));
            bindings.put(key, new WeakReference(binding));
        } else {
            binding = bindings.get(key).get();
        }
        return binding;
    }

    private boolean bindingExists(Object key) {
        return bindings.containsKey(key) && bindings.get(key).get() != null;
    }

    private Object getKeyForTarget(Object target) {
        return target.getClass();
    }

    // Remove binding when not used
    private static BindingAbandonedListener createBindingAbandonedListener(final Object key) {
        return new BindingAbandonedListener() {
            @Override
            public void onBindingAbandoned() {
                bindings.remove(key);
                Log.d(TAG, "Removed binding for: " + key.toString());
                Log.d(TAG, "Bindings size: " + bindings.size());
            }
        };
    }

    /**
     * Get view holder for provided target. Will be created if not created already.
     * @param target annotated view model
     * @param viewFinder view finder handling the view holder
     * @return view holder for target
     */
    private Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if (viewHolder == null) {
            viewHolder = viewHolderFactory.createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

}
