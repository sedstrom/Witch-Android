package se.snylt.zipper.viewbinder;

import se.snylt.zipper.ClassUtils;

public class BinderFactory {

    /**
     * Creates a binding from annotated target view model
     * @param target the view model
     * @return binding
     */
    public Binder createBinder(Object target) {
        try {
            Class clazz = ClassUtils.findBinder(target);
            return ((BindingCreator) clazz.newInstance()).createBinder();
        } catch (Exception e) {
            throw new BinderNotFoundException("Could not find binder for " + target.getClass().getName());
        }
    }
}
