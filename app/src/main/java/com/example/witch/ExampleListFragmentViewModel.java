package com.example.witch;

import java.util.List;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToRecyclerView;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

public class ExampleListFragmentViewModel {

    @BindToRecyclerView(id = R.id.example_list_fragment_recycler_view, adapter = RecyclerViewBinderAdapter.class, set = "items")
    public final List<ExampleItem> items;

    @BindTo(R.id.main_activity_fragment_container)
    public String fragment;

    public ExampleListFragmentViewModel(List<ExampleItem> items) {
        this.items = items;
    }
}