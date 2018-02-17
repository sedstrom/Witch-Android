package se.snylt.witchprocessortest;

import android.view.View;

class OnBindViewModel extends TestViewModel {

    final String onBind = "a";

    String onBindEach = "b";

    OnBindViewModel() {
        super(View.class);
    }
}
