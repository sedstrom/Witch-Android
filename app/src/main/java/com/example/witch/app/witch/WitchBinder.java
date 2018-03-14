package com.example.witch.app.witch;

import se.snylt.witch.viewbinder.Witch;
import se.snylt.witchcore.viewfinder.ViewFinder;

public class WitchBinder {

    private final ViewFinder viewFinder;

    public WitchBinder(ViewFinder viewFinder) {
        this.viewFinder = viewFinder;
    }

    public void bind() {
        Witch.bind(this, viewFinder);
    }

}
