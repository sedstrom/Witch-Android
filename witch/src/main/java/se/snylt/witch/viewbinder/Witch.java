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
        witch().doBind(target, viewFinder, mods);
    }
}