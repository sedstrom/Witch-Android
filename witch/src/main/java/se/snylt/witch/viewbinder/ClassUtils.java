package se.snylt.witch.viewbinder;

public class ClassUtils {

    private final static String VIEW_HOLDER_SUFFIX = "_ViewHolder";

    private final static String VIEW_BINDER_SUFFIX = "_ViewBinder";

    private final static char NOT_A_DOT = '$';

    private final static char DOT = '.';

    static Class findBinder(Object target) throws ClassNotFoundException {
        return Class.forName(classNameForTarget(target) + VIEW_BINDER_SUFFIX);
    }

    static Class findViewHolder(Object target) throws ClassNotFoundException {
        return Class.forName(classNameForTarget(target) + VIEW_HOLDER_SUFFIX);
    }

    private static String classNameForTarget(Object target) {
        String packageName = target.getClass().getPackage().getName();
        String className = target.getClass().getName();
        className = className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
        return packageName + DOT + className;
    }
}
