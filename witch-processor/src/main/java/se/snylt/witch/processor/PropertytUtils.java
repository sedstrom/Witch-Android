package se.snylt.witch.processor;

public class PropertytUtils {

    public static String getPropertySetter(String property) {
        return "set" + capitalize(property);
    }

    public static String getBinderAccessor(String value) {
        return "bind" + capitalize(stripGet(value));
    }

    public static String stripGet(String s) {
        s = s.replaceFirst("^get", "");
        s = Character.toLowerCase(s.charAt(0)) + s.substring(1);
        return s;
    }

    private static String capitalize(String s) {
        return s.toUpperCase().charAt(0) + ((s.length() > 0) ? s.substring(1) : "");
    }

}
