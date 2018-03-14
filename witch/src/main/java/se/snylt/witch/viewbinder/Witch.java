package se.snylt.witch.viewbinder;

import android.app.Activity;
import android.app.Fragment;
import android.view.View;

import se.snylt.witchcore.BinderFactory;
import se.snylt.witchcore.ViewHolderFactory;
import se.snylt.witchcore.WitchCore;
import se.snylt.witchcore.viewfinder.ViewFinder;

public class Witch {

    private static WitchCore INSTANCE;

    static LooperHelper looperHelper = new AndroidLooperHelper();

    public final static int VIEW_HOLDER_TAG_DEFAULT = Integer.MIN_VALUE;

    private static WitchCore witch() {
        if(INSTANCE == null) {
            INSTANCE = new WitchCore(new ViewHolderFactory(), new BinderFactory(), new AndroidLogger());
        }
        return INSTANCE;
    }

    // Test
    public static WitchCore witch(WitchCore witchCore) {
        INSTANCE = witchCore;
        return witch();
    }

    /**
     * Enable or disable logging.
     */
    public static void setLoggingEnabled(boolean enabled) {
        witch().setLoggingEnabled(enabled);
    }

    /**
     * Check if logging is enabled
     */
    public static boolean isLoggingEnabled() {
        return witch().isLoggingEnabled();
    }


    /**
     * Like {@link #bind(Object, Activity)} but more majestic.
     */
    public static void spellBind(Object target, Activity activity) {
        bind(target, activity);
    }

    /**
     * Bind annotated values in {@code binder} to views in {@code activity}
     *
     * @param target view model
     * @param activity activity containing views specified in {@code binder}
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
     * Bind annotated values in {@code binder} to views in {@code view}
     *
     * @param target view model
     * @param view view containing child-views specified in {@code binder}
     */
    public static void bind(Object target, View view) {
        bind(target, viewFinder(view));
    }

    public static ViewFinder viewFinder(Activity activity) {
        return ViewViewFinder.from(activity.findViewById(android.R.id.content), VIEW_HOLDER_TAG_DEFAULT);
    }

    public static ViewFinder viewFinder(View view) {
        return ViewViewFinder.from(view, VIEW_HOLDER_TAG_DEFAULT);
    }

    public static void bind(Object target, ViewFinder viewFinder) {
        assertMainThread();
        witch().doBind(target, viewFinder);
    }

    private static void assertMainThread() {
        if(!looperHelper.isCurrentLooperMainLooper()) {
            throw new IllegalStateException("Calls to Witch can only happen on main thread.");
        }
    }
}