package se.snylt.witch.processor;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;

import se.snylt.witch.processor.valueaccessor.FieldAccessor;
import se.snylt.witch.processor.valueaccessor.MethodAccessor;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;

public class PropertytUtils {

    public static String getPropertySetter(String property) {
        return "set" + capitalize(property);
    }

    static String stripGet(String s) {
        s = s.replaceFirst("^get", "");
        s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    static boolean bindsValue(Element valueAccessor, Element bindAccessor) {
        String va = "binds" + capitalize(stripGet(valueAccessor.getSimpleName().toString()));
        String ba = stripGet(bindAccessor.getSimpleName().toString());
        return va.equals(ba);
    }

    private static String capitalize(String s) {
        return s.toUpperCase().charAt(0) + ((s.length() > 0) ? s.substring(1) : "");
    }

    private static boolean notPrivateOrProtected(Element e) {
        Set<Modifier> modifiers = e.getModifiers();
        return !modifiers.contains(Modifier.PRIVATE) || !modifiers.contains(Modifier.PROTECTED);
    }

    private static boolean isAccessibleMethod(Element value) {
        return value.getKind() == ElementKind.METHOD && notPrivateOrProtected(value);
    }

    private static boolean isAccessibleField(Element value) {
        return value.getKind().isField() && notPrivateOrProtected(value);
    }

    static PropertyAccessor getPropertyAccessor(Element element) {
        if (isAccessibleMethod(element)) {
            return new MethodAccessor(element.getSimpleName().toString());
        }

        if (isAccessibleField(element)) {
            return new FieldAccessor(element.getSimpleName().toString());
        }

        return null;
    }
}
