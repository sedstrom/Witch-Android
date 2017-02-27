package se.snylt.zipperprocessortest;

import android.view.View;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.OnBind;
import se.snylt.zipper.annotations.OnBindEach;
import se.snylt.zipperprocessortest.bindactions.TestOnBindAction;
import se.snylt.zipperprocessortest.bindactions.TestOnPostBindAction;
import se.snylt.zipperprocessortest.bindactions.TestOnPreBindAction;

public class OnBindViewModel extends TestViewModel {

    @BindTo(1)
    @OnBind(TestOnBindAction.class)
    public final String onBind = "a";

    @BindTo(2)
    @OnBindEach({TestOnPreBindAction.class, TestOnBindAction.class, TestOnPostBindAction.class})
    public String onBindEach = "b";

    public OnBindViewModel() {
        super(View.class);
    }
}
