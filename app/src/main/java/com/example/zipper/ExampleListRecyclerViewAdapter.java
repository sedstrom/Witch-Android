package com.example.zipper;

import com.example.zipper.recyclerview.SimpleViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import se.snylt.zipper.viewbinder.Zipper;

public class ExampleListRecyclerViewAdapter extends RecyclerView.Adapter<SimpleViewHolder> {

    private List<ExampleItem> items;

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item, parent, false));
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        Zipper.bind(items.get(position), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void setItems(List<ExampleItem> items) {
        this.items = items;
    }
}
