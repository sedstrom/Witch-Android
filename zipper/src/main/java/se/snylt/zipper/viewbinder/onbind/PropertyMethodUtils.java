package se.snylt.zipper.viewbinder.onbind;

import org.apache.commons.lang3.reflect.MethodUtils;

import android.view.View;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class PropertyMethodUtils {

    public static void setProperty(View view, String property, Object value) {
        Method m = MethodUtils.getMatchingAccessibleMethod(view.getClass(), getPropertySetter(property), value.getClass());
        if (m != null) {
            try {
                m.invoke(view, value);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private static String getPropertySetter(String property) {
        String firstUpperCase = property.toUpperCase().charAt(0) + ((property.length() > 0) ? property.substring(1) : "");
        return "set" + firstUpperCase;
    }
}
