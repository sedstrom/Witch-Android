package se.snylt.witch.processor.utils;

import com.squareup.javapoet.ClassName;

import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;

import static javax.lang.model.element.ElementKind.PACKAGE;

public class FileUtils {

    private final static String VIEW_HOLDER_SUFFIX = "_ViewHolder";

    private final static String VIEW_BINDER_SUFFIX = "_ViewBinder";

    private final static String MAGIC_VIEW_BINDER_PREFIX = "Magic";

    private final static char NOT_A_DOT = '$';

    private final static char DOT = '.';

    private static String getViewHolderName(Element element) {
        return getEncodedElementClassName(element) + VIEW_HOLDER_SUFFIX;
    }

    private static String getBinderName(Element element) {
        return getEncodedElementClassName(element) + VIEW_BINDER_SUFFIX;
    }

    private static String getEncodedElementClassName(Element element) {
        String packageName = getElementPackage(element);
        String className = ((TypeElement) element).getQualifiedName().toString();
        return className.substring(packageName.length() + 1).replace(DOT, NOT_A_DOT);
    }

    private static String getTargetName(Element element) {
        return ((TypeElement) element).getQualifiedName().toString();
    }

    private static String getElementPackage(Element element) {
        while (element.getKind() != PACKAGE) {
            element = element.getEnclosingElement();
        }
        return ((PackageElement) element).getQualifiedName().toString();
    }

    public static ClassName getBindingViewHolderName(Element target) {
        String className = FileUtils.getViewHolderName(target);
        String packageName = FileUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }

    public static ClassName getTargetViewBinderClassName(Element target) {
        String className = FileUtils.getBinderName(target);
        String packageName = FileUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }

    public static ClassName getMagicTargetViewBinderClassName(Element target) {
        String className = MAGIC_VIEW_BINDER_PREFIX + target.getSimpleName().toString();
        String packageName = FileUtils.getElementPackage(target);
        return ClassName.get(packageName,className);
    }

    public static ClassName getElementClassName(Element target) {
        String className = FileUtils.getTargetName(target);
        String packageName = FileUtils.getElementPackage(target);
        return ClassName.get(packageName, className);
    }
}
