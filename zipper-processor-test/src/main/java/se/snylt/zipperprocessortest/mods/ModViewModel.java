package se.snylt.zipperprocessortest.mods;

import android.view.View;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipperprocessortest.TestViewModel;

public class ModViewModel extends TestViewModel {

    @BindTo(1)
    public final String something = "something";

    public ModViewModel() {
        super(View.class);
    }
}
