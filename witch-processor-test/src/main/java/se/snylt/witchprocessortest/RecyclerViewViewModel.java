package se.snylt.witchprocessortest;

import android.support.v7.widget.RecyclerView;

class RecyclerViewViewModel extends TestViewModel {

    final Object items = new Object();

    RecyclerViewViewModel() {
        super(RecyclerView.class);
    }
}
