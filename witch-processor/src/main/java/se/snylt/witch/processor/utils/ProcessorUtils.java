package se.snylt.witch.processor.utils;

import java.util.Set;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import se.snylt.witch.processor.WitchException;
import se.snylt.witch.processor.valueaccessor.FieldAccessor;
import se.snylt.witch.processor.valueaccessor.MethodAccessor;
import se.snylt.witch.processor.valueaccessor.PropertyAccessor;


public class ProcessorUtils {

    public static String getPropertySetter(String property) {
        return "set" + capitalize(property);
    }

    private static String capitalize(String s) {
        return s.toUpperCase().charAt(0) + ((s.length() > 0) ? s.substring(1) : "");
    }

    private static boolean notPrivateOrProtected(Element e) {
        Set<Modifier> modifiers = e.getModifiers();
        return !modifiers.contains(Modifier.PRIVATE) && !modifiers.contains(Modifier.PROTECTED);
    }

    static boolean isAccessibleMethod(Element e) {
        return e.getKind() == ElementKind.METHOD && notPrivateOrProtected(e);
    }

    private static boolean isAccessibleField(Element e) {
        return e.getKind().isField() && notPrivateOrProtected(e);
    }

    public static String getPropertyName(Element element) {
        return element.getSimpleName().toString();
    }

    public static PropertyAccessor getPropertyAccessor(Element element) throws WitchException {
        if (isAccessibleMethod(element)) {
            return new MethodAccessor(element.getSimpleName().toString());
        }

        if (isAccessibleField(element)) {
            return new FieldAccessor(element.getSimpleName().toString());
        }

        throw WitchException.invalidValueAccessor(element);
    }
}
