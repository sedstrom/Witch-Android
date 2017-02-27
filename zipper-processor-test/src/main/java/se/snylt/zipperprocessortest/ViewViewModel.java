package se.snylt.zipperprocessortest;

import android.view.View;

import se.snylt.zipper.annotations.BindToView;

public class ViewViewModel extends TestViewModel {

    @BindToView(id = 666, view = View.class, set = "visibility")
    public final Integer visibility = View.VISIBLE;

    public ViewViewModel() {
        super(View.class);
    }
}
