package se.snylt.witch.viewbinder;

import android.util.Log;

import se.snylt.witch.ClassUtils;

import static se.snylt.witch.viewbinder.WitchCore.TAG;

public class BinderFactory {

    /**
     * Creates a binding from annotated target view model
     * @param target the view model
     * @return binding
     */
    public TargetViewBinder createBinder(Object target) {
        try {
            Class clazz = ClassUtils.findBinder(target);
            return ((TargetViewBinderFactory) clazz.newInstance()).createBinder();
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            throw new BinderNotFoundException("Could not find binder for " + target.getClass().getName());
        }
    }
}
