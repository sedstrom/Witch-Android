package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindData;

public class ViewViewModel extends TestViewModel {

    @BindData(id = android.R.id.home, view = View.class, set = "visibility")
    public final Integer visibility = View.VISIBLE;

    public ViewViewModel() {
        super(View.class);
    }
}
