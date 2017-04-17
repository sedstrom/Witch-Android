package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.AlwaysBind;
import se.snylt.witch.annotations.BindTo;

class AlwaysBindViewModel extends TestViewModel {

    @BindTo(android.R.id.button1)
    @AlwaysBind
    final String something = "something";

    AlwaysBindViewModel() {
        super(View.class);
    }
}