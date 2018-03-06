package se.snylt.witch.viewbinder;


import android.app.Activity;
import android.view.View;

import se.snylt.witchcore.viewfinder.ViewFinder;

public abstract class MagicTargetViewBinder<Binder> {

    protected final Binder binder;

    private final ViewFinder viewFinder;

    MagicTargetViewBinder(Binder binder, ViewFinder viewFinder) {
        this.binder = binder;
        this.viewFinder = viewFinder;
    }

    public MagicTargetViewBinder(Binder binder, Activity activity) {
        this(binder, Witch.viewFinder(activity));
    }

    public MagicTargetViewBinder(Binder binder, View view) {
        this(binder, Witch.viewFinder(view));
    }

    public void bind() {
        Witch.bind(binder, viewFinder);
    }

    public Binder getBinder() {
        return binder;
    }
}
