package se.snylt.zipper;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.ElementKind.PACKAGE;

public class ClassUtils {

    private final static String CLASS_SUFFIX = "_ViewBinding";

    private final static char NOT_A_DOT = '$';

    private final static char DOT = '.';

    public static Class findBinding(Object target) throws ClassNotFoundException {
        String packageName = target.getClass().getPackage().getName();
        String className = target.getClass().getName().toString();
        className = className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
        return Class.forName(packageName + DOT + className + CLASS_SUFFIX);
    }

    public static String getBindingName(Element element) {
        String packageName = getBindingPackage(element);
        String className = ((TypeElement) element).getQualifiedName().toString();
        className = className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
        return className + CLASS_SUFFIX;
    }

    public static String getBindingPackage(Element element) {
        while (element.getKind() != PACKAGE) {
            element = element.getEnclosingElement();
        }
        return ((PackageElement) element).getQualifiedName().toString();
    }
}
