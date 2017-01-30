package com.example.zipper.recyclerview;

import com.example.zipper.R;

import java.util.List;

import se.snylt.zipper.annotations.BindToRecyclerView;

public class RecyclerViewViewModel {

    @BindToRecyclerView(id= R.id.recycler_view_fragment_recycler_view, adapter = MyRecyclerViewAdapter.class, set = "items")
    public final List<MyRecyclerViewAdapter.MyItem> items;

    public RecyclerViewViewModel(List<MyRecyclerViewAdapter.MyItem> items) {
        this.items = items;
    }
}
