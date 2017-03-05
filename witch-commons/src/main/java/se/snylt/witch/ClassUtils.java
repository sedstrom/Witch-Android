package se.snylt.witch;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.ElementKind.PACKAGE;

public class ClassUtils {

    private final static String VIEW_HOLDER_SUFFIX = "_ViewHolder";

    private final static String VIEW_BINDER_SUFFIX = "_ViewBinder";

    private final static char NOT_A_DOT = '$';

    private final static char DOT = '.';

    public static Class findBinder(Object target) throws ClassNotFoundException {
        return Class.forName(classNameForTarget(target) + VIEW_BINDER_SUFFIX);
    }

    public static Class findViewHolder(Object target) throws ClassNotFoundException {
        return Class.forName(classNameForTarget(target) + VIEW_HOLDER_SUFFIX);
    }

    private static String classNameForTarget(Object target) {
        String packageName = target.getClass().getPackage().getName();
        String className = target.getClass().getName().toString();
        className = className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
        return packageName + DOT + className;
    }

    public static String getViewHolderName(Element element) {
        return getElementClassName(element) + VIEW_HOLDER_SUFFIX;
    }

    public static String getBinderName(Element element) {
        return getElementClassName(element) + VIEW_BINDER_SUFFIX;
    }

    private static String getElementClassName(Element element) {
        String packageName = getElementPackage(element);
        String className = ((TypeElement) element).getQualifiedName().toString();
        return className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
    }

    public static String getTargetName(Element element) {
        String className = ((TypeElement) element).getQualifiedName().toString();
        return className;
    }

    public static String getElementPackage(Element element) {
        while (element.getKind() != PACKAGE) {
            element = element.getEnclosingElement();
        }
        return ((PackageElement) element).getQualifiedName().toString();
    }
}
