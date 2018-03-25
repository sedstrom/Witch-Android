package se.snylt.witchcore;

public class BinderFactory {

    /**
     * Creates a binder from annotated target
     * @param target annotated class
     * @return binder that binds target
     */
    public TargetViewBinder createBinder(Object target) {
        try {
            Class clazz = ClassUtils.findBinder(target);
            return ((TargetViewBinderFactory) clazz.newInstance()).createBinder();
        } catch (Exception e) {
            throw new BinderNotFoundException("Could not find binder for " + target.getClass().getName());
        }
    }
}
