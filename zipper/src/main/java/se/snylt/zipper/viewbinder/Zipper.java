package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;

import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.viewfinder.ActivityViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewViewFinder;

public class Zipper {

    private final static String TAG = "Zipper";

    public final static int VIEW_HOLDER_TAG = Integer.MIN_VALUE;

    private final static HashMap<Object, Binding> bindings = new HashMap<>();

    public static Binding bind(Object target, Activity activity) {
        return bind(target, new ActivityViewFinder(activity));
    }

    public static Binding bind(Object target, View view) {
        return bind(target, new ViewViewFinder(view));
    }

    private static Binding bind(Object target, ViewFinder viewFinder) {
        Binding binding = getOrCreateBinding(target);
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        for (ViewBinder viewBinder : binding.getViewBinders()) {
            View view = findView(viewBinder, viewHolder, viewFinder);
            Object value = viewBinder.getValue(target);
            viewBinder.doBind(view, value);
        }
        return binding;
    }

    private static Binding getOrCreateBinding(Object target) {
        final Class key = target.getClass();
        Binding binding;
        if(!bindings.containsKey(target.getClass())) {
            binding = createBinding(target, new OnUnbindListener() {
                @Override
                public void onUnbind() {
                    doUnbind(key);
                }
            });
            bindings.put(key, binding);
        }
        binding = bindings.get(key);
        return binding;
    }

    private static void doUnbind(Class<?> targetClass) {
        Log.d(TAG, "remove binding: " + targetClass.getSimpleName());
        bindings.remove(targetClass);
    }

    private static Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if(viewHolder == null) {
            viewHolder = createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

    private static View findView(ViewBinder viewBinder, Object viewHolder, ViewFinder viewFinder) {
        if(viewBinder.getView(viewHolder) == null) {
            Log.d(TAG, "looking up view with id: " + viewBinder.viewId);
            viewBinder.setView(viewHolder, viewFinder.findViewById(viewBinder.viewId));
        }
        return (View) viewBinder.getView(viewHolder);
    }

    private static Binding createBinding(Object target, OnUnbindListener listener) {
        Log.d(TAG, "created binding for: " + target.getClass().getSimpleName());
        try {
            Class clazz = ClassUtils.findBinding(target);
            return ((BindingCreator) clazz.newInstance()).createBinding(listener);
        } catch (Exception e) {
            throw new BindingNotFoundException(target);
        }
    }

    private static Object createViewHolder(Object target) {
        Log.d(TAG, "created binding for: " + target.getClass().getSimpleName());
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

}
