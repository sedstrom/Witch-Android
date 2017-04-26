package se.snylt.witch.viewbinder;

class BinderFactory {

    /**
     * Creates a binding from annotated target view model
     * @param target the view model
     * @return binder
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
