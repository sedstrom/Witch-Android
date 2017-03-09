package se.snylt.witch.viewbinder;

import android.app.Activity;
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
    protected static WitchCore witch(WitchCore witchCore) {
        INSTANCE = witchCore;
        return witch();
    }

    /**
     * Like {@link #bind(Object, Activity, Object...)} but more majestic.
     */
    public static void spellBind(Object target, Activity activity, Object ...mods) {
        bind(target, activity, mods);
    }

    /**
     * Binds annotated values in {@code target} to views in {@code activity} with additional mods.
     *
     * @param target view model
     * @param activity activity containing views specified in {@code target}
     * @param mods additional bind actions
     */
    public static void bind(Object target, Activity activity, Object ...mods) {
        bind(target, viewFinder(activity), mods);
    }

    /**
     * Like {@link #bind(Object, View, Object...)} but more majestic.
     */
    public static void spellBind(Object target, View view, Object ...mods) {
        bind(target, view, mods);
    }

    /**
     * Binds annotated values in {@code target} to views in {@code view} with additional mods.
     *
     * @param target view model
     * @param view view containing child-views specified in {@code target}
     * @param mods additional bind actions
     */
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
        witch().doBind(target, viewFinder, mods);
    }
}