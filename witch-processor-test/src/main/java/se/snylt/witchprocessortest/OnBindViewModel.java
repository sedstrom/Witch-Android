package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.OnBind;
import se.snylt.witch.annotations.OnBindEach;
import se.snylt.witchprocessortest.bindactions.TestOnBind;

class OnBindViewModel extends TestViewModel {

    @BindTo(android.R.id.button1)
    @OnBind(TestOnBind.class)
    final String onBind = "a";

    @BindTo(android.R.id.button2)
    @OnBindEach({TestOnBind.class, TestOnBind.class, TestOnBind.class})
    String onBindEach = "b";

    OnBindViewModel() {
        super(View.class);
    }
}
