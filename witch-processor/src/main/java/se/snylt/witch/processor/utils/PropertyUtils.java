package se.snylt.witch.processor.utils;

import com.squareup.javapoet.TypeName;

import java.util.Set;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

import se.snylt.witch.processor.valueaccessor.FieldAccessor;
import se.snylt.witch.processor.valueaccessor.MethodAccessor;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;


public class PropertyUtils {

    public static String getPropertySetter(String property) {
        return "set" + capitalize(property);
    }

    static String stripGet(String s) {
        s = s.replaceFirst("^get", "");
        s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    public static boolean bindsValue(Element valueAccessor, Element bindsAccessor) {
        String va = "binds" + capitalize(stripGet(valueAccessor.getSimpleName().toString()));
        String ba = stripGet(bindsAccessor.getSimpleName().toString());
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

    public static String getPropertyName(Element element) {
        return element.getSimpleName().toString();
    }

    public static PropertyAccessor getPropertyAccessor(Element element) {
        if (isAccessibleMethod(element)) {
            return new MethodAccessor(element.getSimpleName().toString());
        }

        if (isAccessibleField(element)) {
            return new FieldAccessor(element.getSimpleName().toString());
        }

        return null;
    }
}
