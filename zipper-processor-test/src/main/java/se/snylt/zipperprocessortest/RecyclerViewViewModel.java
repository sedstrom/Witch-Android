package se.snylt.zipperprocessortest;

import android.support.v7.widget.RecyclerView;

import se.snylt.zipper.annotations.BindToRecyclerView;

public class RecyclerViewViewModel extends TestViewModel {

    @BindToRecyclerView(id = 1, adapter = RecyclerViewAdapter.class, set = "items")
    public final Object items = new Object();

    public RecyclerViewViewModel() {
        super(RecyclerView.class);
    }
}
