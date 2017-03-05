package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.AlwaysBind;
import se.snylt.witch.annotations.BindTo;

public class AlwaysBindViewModel extends TestViewModel {

    @BindTo(android.R.id.button1)
    @AlwaysBind
    public final String something = "something";

    public AlwaysBindViewModel() {
        super(View.class);
    }
}