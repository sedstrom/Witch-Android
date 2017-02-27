package se.snylt.zipperprocessortest;

import android.view.View;

import se.snylt.zipper.annotations.AlwaysBind;
import se.snylt.zipper.annotations.BindTo;

public class AlwaysBindViewModel extends TestViewModel {

    @BindTo(1)
    @AlwaysBind
    public final String something = "something";

    public AlwaysBindViewModel() {
        super(View.class);
    }
}