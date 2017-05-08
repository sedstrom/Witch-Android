package com.example.witch;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.recyclerview.EmptyViewHolder;

class ExampleListRecyclerViewAdapter extends RecyclerView.Adapter<EmptyViewHolder> {

    private List<ExampleItem> items;

    @Override
    public EmptyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.example_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(EmptyViewHolder holder, int position) {
        Witch.bind(items.get(position), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<ExampleItem> items) {
        this.items = items;
    }
}
