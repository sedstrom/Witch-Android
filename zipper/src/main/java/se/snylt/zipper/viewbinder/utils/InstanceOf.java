package se.snylt.zipper.viewbinder.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public class InstanceOf {

    public static boolean integer(Object o) {
        return o instanceof Integer;
    }

    public static boolean string(Object o) {
        return o instanceof String;
    }

    public static boolean charSequence(Object o) {
        return o instanceof CharSequence;
    }

    public static boolean drawable(Object o) {
        return o instanceof Drawable;
    }

    public static boolean bitmap(Object o) {
        return o instanceof Bitmap;
    }

    public static boolean bool(Object o) {
        return o instanceof Boolean;
    }

}
