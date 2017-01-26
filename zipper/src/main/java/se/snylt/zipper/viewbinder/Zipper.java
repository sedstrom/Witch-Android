package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import java.lang.reflect.Field;
import java.util.List;

import se.snylt.zipper.BindAction;
import se.snylt.zipper.BindingSpec;
import se.snylt.zipper.BindingSpecFactory;
import se.snylt.zipper.BindingViewHolder;
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
        BindingViewHolder viewHolder = getOrCreateViewHolder(target, viewFinder);
        for (BindingSpec bindingSpec : createBinding(target).getBindingSpecs()) {
            String key = bindingSpec.key;
            View view = findView(bindingSpec, viewHolder, viewFinder);
            List<BindAction> actions;
            if((actions = viewHolder.getActions(key)) == null) {
                actions = bindingSpec.getBindActions();
                viewHolder.putActions(key, actions);
            }
            bind(actions, view, getValue(target, bindingSpec.key));
        }
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

    // TODO FIX
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

    private static BindingViewHolder createViewHolder(Object target) {
        try {
            Class clazz = ClassUtils.findViewHolder(target);
            return (BindingViewHolder)clazz.newInstance();
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not find binding for " + target.getClass().getName());
        }
    }

    private static Object getValue(Object target, String value) {
        try {
            Field f = target.getClass().getDeclaredField(value);
            return f.get(target);
        } catch (Exception e) {
            throw new BindingNotFoundException("Could not read value from " + target.getClass().getName());
        }
    }

}
