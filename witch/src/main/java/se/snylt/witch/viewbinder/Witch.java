package se.snylt.witch.viewbinder;

import android.app.Activity;
import android.os.Looper;
import android.view.View;

import se.snylt.witch.viewbinder.viewfinder.ViewFinder;
import se.snylt.witch.viewbinder.viewfinder.ViewViewFinder;

public class Witch {

    private static WitchCore INSTANCE;

    public final static int VIEW_HOLDER_TAG_DEFAULT = Integer.MIN_VALUE;

    private static WitchCore witch() {
        if(INSTANCE == null) {
            INSTANCE = new WitchCore(new ViewHolderFactory(), new BinderFactory());
        }
        return INSTANCE;
    }

    // Test
    public static WitchCore witch(WitchCore witchCore) {
        INSTANCE = witchCore;
        return witch();
    }

    /**
     * Like {@link #bind(Object, Activity)} but more majestic.
     */
    public static void spellBind(Object target, Activity activity) {
        bind(target, activity);
    }

    /**
     * Binds annotated values in {@code target} to views in {@code activity}
     *
     * @param target view model
     * @param activity activity containing views specified in {@code target}
     */
    public static void bind(Object target, Activity activity) {
        bind(target, viewFinder(activity));
    }

    /**
     * Like {@link #bind(Object, View)} but more majestic.
     */
    public static void spellBind(Object target, View view) {
        bind(target, view);
    }

    /**
     * Binds annotated values in {@code target} to views in {@code view}
     *
     * @param target view model
     * @param view view containing child-views specified in {@code target}
     */
    public static void bind(Object target, View view) {
        bind(target, viewFinder(view));
    }

    private static ViewFinder viewFinder(Activity activity) {
        return ViewViewFinder.from(activity.findViewById(android.R.id.content), VIEW_HOLDER_TAG_DEFAULT);
    }

    private static ViewFinder viewFinder(View view) {
        return ViewViewFinder.from(view, VIEW_HOLDER_TAG_DEFAULT);
    }

    private static void bind(Object target, ViewFinder viewFinder) {
        assertMainThread();
        witch().doBind(target, viewFinder);
    }

    private static void assertMainThread() {
        if(Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("Calls to Witch can only happen on main thread.");
        }
    }
}