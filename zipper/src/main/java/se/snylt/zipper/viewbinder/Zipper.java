package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.HashMap;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.viewfinder.ActivityViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewViewFinder;

public class Zipper {

    private final static String TAG = "Zipper";

    public final static int VIEW_HOLDER_TAG = Integer.MIN_VALUE;

    private final static HashMap<Object, WeakReference<Binding>> bindings = new HashMap<>();

    public static Binding bind(Object target, Activity activity) {
        return bind(target, new ActivityViewFinder(activity));
    }

    public static Binding bind(Object target, View view) {
        return bind(target, new ViewViewFinder(view));
    }

    /**
     * Bind target values to views in provided view finder
     * @param target view model with bindings defined
     * @param viewFinder view finder containing views defined in target.
     * @return complete binding
     */
    private static Binding bind(Object target, ViewFinder viewFinder) {
        Binding binding = getOrCreateBinding(target);
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        for (ViewBinder viewBinder : binding.getViewBinders()) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }
        return binding;
    }

    /**
     * Get binding for provided target view model. Creates new binding if not already existing.
     * @param target annotated view model
     * @return binding for target
     */
    private static Binding getOrCreateBinding(Object target) {
        final Class key = target.getClass();
        Binding binding;
        if (!isBindingCreated(key)) {
            binding = createBinding(target);
            bindings.put(key, new WeakReference(binding));
        }
        return bindings.get(key).get();
    }

    private static boolean isBindingCreated(Class key) {
        return bindings.containsKey(key) && bindings.get(key).get() != null;
    }

    private static OnUnbindListener createUnbindCallback(final Class key) {
        return new OnUnbindListener() {
            @Override
            public void onUnbind() {
                doUnbind(key);
            }
        };
    }

    /**
     * Unbind any resources bound to targetClass
     * @param targetClass target class
     */
    private static void doUnbind(Class<?> targetClass) {
        // TODO
    }

    /**
     * Get view holder for provided target. Will be created if not created already.
     * @param target annotated view model
     * @param viewFinder view finder handling the view holder
     * @return view holder for target
     */
    private static Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if (viewHolder == null) {
            viewHolder = createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

    /**
     * Creates a binding from annotated target view model
     * @param target the view model
     * @return binding
     */
    private static Binding createBinding(Object target) {
        try {
            Class clazz = ClassUtils.findBinding(target);
            return ((BindingCreator) clazz.newInstance()).createBinding(createUnbindCallback(target.getClass()));
        } catch (Exception e) {
            throw new BindingNotFoundException(target);
        }
    }

    /**
     * Creates a view holder from annotated target view model
     * @param target the view model
     * @return view holder for views defined in target
     */
    private static Object createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

}
