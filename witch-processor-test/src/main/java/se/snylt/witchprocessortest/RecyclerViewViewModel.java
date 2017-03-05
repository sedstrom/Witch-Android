package se.snylt.witchprocessortest;

import android.support.v7.widget.RecyclerView;

import se.snylt.witch.annotations.BindToRecyclerView;

public class RecyclerViewViewModel extends TestViewModel {

    @BindToRecyclerView(id = android.R.id.button1, adapter = RecyclerViewAdapter.class, set = "items")
    public final Object items = new Object();

    public RecyclerViewViewModel() {
        super(RecyclerView.class);
    }
}
