package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;

import se.snylt.zipper.BindAction;
import se.snylt.zipper.BindingSpec;
import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.onbind.OnBind;

public class Zipper {

    public final static int VIEW_HOLDER_TAG = Integer.MIN_VALUE;

    public static void bind(Object target, Activity activity) {
        bind(target, new ActivityViewFinder(activity));
    }

    public static void bind(Object target, View view) {
        bind(target, new ViewViewFinder(view));
    }

    private static void bind(Object target, ViewFinder viewFinder) {
        for (BindingSpec bindingSpec : createBinding(target).getBindingSpecs()) {
            View view = findView(target, bindingSpec, viewFinder);
            bind(bindingSpec, view, target);
        }
    }

    private static Object getOrCreateViewHolder(Object target, ViewFinder viewFinder) {
        Object viewHolder = viewFinder.getViewHolder();
        if(viewHolder == null) {
            viewHolder = createViewHolder(target);
            viewFinder.setViewHolder(viewHolder);
        }
        return viewHolder;
    }

    private static View findView(Object target, BindingSpec bindingSpec, ViewFinder viewFinder) {
        Object viewHolder = getOrCreateViewHolder(target, viewFinder);
        bindingSpec.setView(viewHolder, viewFinder.findViewById(bindingSpec.viewId));
        return (View) bindingSpec.getView(viewHolder);
    }

    private static void bind(BindingSpec bindingSpec, View view, Object target) {
        Object value = getValue(target, bindingSpec);
        for (BindAction bindAction : bindingSpec.getBindActions()) {
            doBind(bindAction, view, value);
        }
    }

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

    private static BindingSpecFactory createBinding(Object target) {
        try {
            Class clazz = ClassUtils.findBinding(target);
            return (BindingSpecFactory) clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

    private static Object createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

    private static Object getValue(Object target, BindingSpec bindingSpec) {
        try {
            Field f = target.getClass().getDeclaredField(bindingSpec.key);
            return f.get(target);
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not read value from " + target.getClass().getName());
        }
    }

}
