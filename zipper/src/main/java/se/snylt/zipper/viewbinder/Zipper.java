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

    public static NewBinding bind(Object target, Activity activity) {
        return bind(target, viewFinder(activity, activity));
    }

    public static NewBinding bind(Object target, Activity activity, Object user) {
        return bind(target, viewFinder(activity, user));
    }

    public static NewBinding bind(Object target, View view) {
        return bind(target, viewFinder(view, view));
    }

    public static NewBinding bind(Object target, View view, Object user) {
        return bind(target, viewFinder(view, user));
    }

    private static ViewFinder viewFinder(Activity activity, Object user) {
        return new ActivityViewFinder(activity, user, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static ViewFinder viewFinder(View view, Object user) {
        return new ViewViewFinder(view, user, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static NewBinding bind(Object target, ViewFinder viewFinder) {
        return zipper().doBind(target, viewFinder);
    }

}
