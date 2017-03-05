package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.OnBind;
import se.snylt.witch.annotations.OnBindEach;
import se.snylt.witchprocessortest.bindactions.TestOnBindAction;
import se.snylt.witchprocessortest.bindactions.TestOnPostBindAction;
import se.snylt.witchprocessortest.bindactions.TestOnPreBindAction;

public class OnBindViewModel extends TestViewModel {

    @BindTo(android.R.id.button1)
    @OnBind(TestOnBindAction.class)
    public final String onBind = "a";

    @BindTo(android.R.id.button2)
    @OnBindEach({TestOnPreBindAction.class, TestOnBindAction.class, TestOnPostBindAction.class})
    public String onBindEach = "b";

    public OnBindViewModel() {
        super(View.class);
    }
}
