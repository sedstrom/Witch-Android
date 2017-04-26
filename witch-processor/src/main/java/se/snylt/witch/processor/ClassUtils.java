package se.snylt.witch.processor;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.ElementKind.PACKAGE;

class ClassUtils {

    private final static String VIEW_HOLDER_SUFFIX = "_ViewHolder";

    private final static String VIEW_BINDER_SUFFIX = "_ViewBinder";

    private final static char NOT_A_DOT = '$';

    private final static char DOT = '.';

    static String getViewHolderName(Element element) {
        return getElementClassName(element) + VIEW_HOLDER_SUFFIX;
    }

    static String getBinderName(Element element) {
        return getElementClassName(element) + VIEW_BINDER_SUFFIX;
    }

    private static String getElementClassName(Element element) {
        String packageName = getElementPackage(element);
        String className = ((TypeElement) element).getQualifiedName().toString();
        return className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
    }

    static String getTargetName(Element element) {
        return ((TypeElement) element).getQualifiedName().toString();
    }

    static String getElementPackage(Element element) {
        while (element.getKind() != PACKAGE) {
            element = element.getEnclosingElement();
        }
        return ((PackageElement) element).getQualifiedName().toString();
    }
}
