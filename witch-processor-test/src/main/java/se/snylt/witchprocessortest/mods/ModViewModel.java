package se.snylt.witchprocessortest.mods;

import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witchprocessortest.TestViewModel;

public class ModViewModel extends TestViewModel {

    @BindTo(1)
    public final String something = "something";

    public ModViewModel() {
        super(View.class);
    }
}
