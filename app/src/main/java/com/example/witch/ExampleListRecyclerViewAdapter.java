package com.example.witch;

import com.example.witch.recyclerview.SimpleViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import se.snylt.witch.viewbinder.Witch;

class ExampleListRecyclerViewAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private List<ExampleItem> items;

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
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
