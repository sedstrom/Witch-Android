package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import se.snylt.zipper.viewbinder.viewfinder.ActivityViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewViewFinder;

public class Zipper {

    private static ZipperCore INSTANCE;

    private final static int VIEW_HOLDER_TAG_DEFAULT = Integer.MIN_VALUE;

    private static ZipperCore zipper() {
        if(INSTANCE == null) {
            INSTANCE = new ZipperCore(new ViewHolderFactory(), new BinderFactory());
        }
        return INSTANCE;
    }

    public static Binding bind(Object target, Activity activity, Object ...mods) {
        return bind(target, viewFinder(activity, activity), mods);
    }

    public static Binding bind(Object target, Object user, Activity activity, Object ...mods) {
        return bind(target, viewFinder(activity, user), mods);
    }

    public static Binding bind(Object target, View view, Object ...mods) {
        return bind(target, viewFinder(view, view), mods);
    }

    public static Binding bind(Object target, Object user, View view, Object ...mods) {
        return bind(target, viewFinder(view, user), mods);
    }

    private static ViewFinder viewFinder(Activity activity, Object user) {
        return new ActivityViewFinder(activity, user, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static ViewFinder viewFinder(View view, Object user) {
        return new ViewViewFinder(view, user, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static Binding bind(Object target, ViewFinder viewFinder, Object ...mods) {
        return zipper().doBind(target, viewFinder, mods);
    }
}
