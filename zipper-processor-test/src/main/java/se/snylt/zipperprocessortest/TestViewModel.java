package se.snylt.zipperprocessortest;

import android.view.View;

public class TestViewModel {

    public final Class<? extends View> VIEW_CLASS;

    public TestViewModel(Class<? extends View> viewClass) {
        VIEW_CLASS = viewClass;
    }
}
