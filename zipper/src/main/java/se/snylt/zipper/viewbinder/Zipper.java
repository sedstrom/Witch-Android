package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.util.HashMap;
import java.util.List;

import se.snylt.zipper.BindAction;
import se.snylt.zipper.BindingSpec;
import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.BindingViewHolder;
import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.onbind.OnBind;

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
        BindingViewHolder viewHolder = getOrCreateViewHolder(target, viewFinder);
        for (BindingSpec bindingSpec : binding.getBindingSpecs()) {
            View view = findView(bindingSpec, viewHolder, viewFinder);
            Object value = bindingSpec.getValue(target);
            bind(bindingSpec.getBindActions(), view, value);
        }
        return binding;
    }

    private static Binding getOrCreateBinding(Object target) {
        final Class key = target.getClass();
        Binding binding;
        if(!bindings.containsKey(target.getClass())) {
            bindings.put(key, new Binding(createBindingSpecs(target), new OnUnbindListener() {
                @Override
                public void onUnbind() {
                    doUnbind(key);
                }
            }));
            Log.d(TAG, "put binding: " +key.getSimpleName());
        }
        binding = bindings.get(key);
        return binding;
    }

    private static void doUnbind(Class<?> targetClass) {
        Log.d(TAG, "remove binding: " + targetClass.getSimpleName());
        bindings.remove(targetClass);
    }

    private static BindingViewHolder getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        BindingViewHolder viewHolder = viewFinder.getViewHolder();
        if(viewHolder == null) {
            viewHolder = createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

    private static View findView(BindingSpec bindingSpec, BindingViewHolder viewHolder, ViewFinder viewFinder) {
        if(bindingSpec.getView(viewHolder) == null) {
            bindingSpec.setView(viewHolder, viewFinder.findViewById(bindingSpec.viewId));
        }
        return (View) bindingSpec.getView(viewHolder);
    }

    private static void bind(List<BindAction> bindActions, View view, Object value) {
        for (BindAction bindAction : bindActions) {
            doBind(bindAction, view, value);
        }
    }

    // TODO Sort
    private static void doBind(BindAction bindAction, View view, Object value) {
        if (bindAction instanceof OnPreBind) {
            ((OnPreBind) bindAction).onPreBind(view, value);
        }
        if (bindAction instanceof OnBind) {
            ((OnBind) bindAction).onBind(view, value);
        }
        if (bindAction instanceof OnPostBind) {
            ((OnPostBind) bindAction).onPostBind(view, value);
        }
    }

    private static List<BindingSpec> createBindingSpecs(Object target) {
        try {
            Class clazz = ClassUtils.findBinding(target);
            return ((BindingSpecFactory) clazz.newInstance()).getBindingSpecs();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

    private static BindingViewHolder createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return (BindingViewHolder)clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

}
