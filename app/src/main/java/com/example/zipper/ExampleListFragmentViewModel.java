package com.example.zipper;

import java.util.List;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToRecyclerView;

public class ExampleListFragmentViewModel {

    @BindToRecyclerView(id = R.id.example_list_fragment_recycler_view, adapter = ExampleListRecyclerViewAdapter.class, set = "items")
    public final List<ExampleItem> items;

    @BindTo(R.id.main_activity_fragment_container)
    public String fragment;

    public ExampleListFragmentViewModel(List<ExampleItem> items) {
        this.items = items;
    }
}