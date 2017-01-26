package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Field;

import se.snylt.zipper.BindAction;
import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.BindingSpec;
import se.snylt.zipper.ClassUtils;
import se.snylt.zipper.viewbinder.onbind.OnBind;

public class Zipper {

    public static void bind(Object target, Activity viewFinder) {
        long start = System.currentTimeMillis();
        for (BindingSpec bindingSpec : getBinding(target).getBindingSpecs()) {
            View view = findView(target, bindingSpec, viewFinder);
            bind(bindingSpec, view, target);
        }
        Log.d("bind", target.getClass().getSimpleName() + " took: " + (System.currentTimeMillis() - start) + "ms");
    }

    public static void bind(Object target, View viewFinder) {
        for (BindingSpec bindingSpec : getBinding(target).getBindingSpecs()) {
            View view = findView(target, bindingSpec, viewFinder);
            bind(bindingSpec, view, target);
        }
    }

    private static View findView(Object target, BindingSpec bindingSpec, View viewFinder) {
        Object viewHolder = getViewHolder(target);
        bindingSpec.setView(viewHolder, viewFinder.findViewById(bindingSpec.viewId));
        return (View) bindingSpec.getView(viewHolder);
    }

    private static View findView(Object target, BindingSpec bindingSpec, Activity viewFinder) {
        Object viewHolder = getViewHolder(target);
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

    private static BindingSpecFactory getBinding(Object target) {
        try {
            Class clazz = ClassUtils.findBinding(target);
            return (BindingSpecFactory) clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

    private static Object getViewHolder(Object target) {
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
