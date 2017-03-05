package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindToView;

public class ViewViewModel extends TestViewModel {

    @BindToView(id = android.R.id.home, view = View.class, set = "visibility")
    public final Integer visibility = View.VISIBLE;

    public ViewViewModel() {
        super(View.class);
    }
}
