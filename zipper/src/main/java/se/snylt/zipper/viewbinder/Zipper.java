package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewViewFinder;

public class Zipper {

    private static ZipperCore INSTANCE;

    public final static int VIEW_HOLDER_TAG_DEFAULT = Integer.MIN_VALUE;

    private static ZipperCore zipper() {
        if(INSTANCE == null) {
            INSTANCE = new ZipperCore(new ViewHolderFactory(), new BinderFactory());
        }
        return INSTANCE;
    }

    // Test
    protected static ZipperCore withCore(ZipperCore zipperCore) {
        INSTANCE = zipperCore;
        return INSTANCE;
    }

    public static void bind(Object target, Activity activity, Object ...mods) {
        bind(target, viewFinder(activity), mods);
    }

    public static void bind(Object target, View view, Object ...mods) {
        bind(target, viewFinder(view), mods);
    }

    private static ViewFinder viewFinder(Activity activity) {
        return new ViewViewFinder(activity.findViewById(android.R.id.content), VIEW_HOLDER_TAG_DEFAULT);
    }

    private static ViewFinder viewFinder(View view) {
        return new ViewViewFinder(view, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static void bind(Object target, ViewFinder viewFinder, Object ...mods) {
        zipper().doBind(target, viewFinder, mods);
    }
}