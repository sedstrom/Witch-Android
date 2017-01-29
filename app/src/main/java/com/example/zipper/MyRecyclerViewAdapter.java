package com.example.zipper;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private MyItem[] items = new MyItem[0];

    private Binding binding;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.my_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        binding = Zipper.bind(items[position], holder.itemView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        if(binding != null) {
            binding.unBind();
        }
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public void setItems(MyItem[] items) {
        this.items = items;
    }

    public static class MyItem {

        @BindToTextView(id = R.id.my_item_title)
        public final String title;

        @BindToTextView(id = R.id.my_item_subtitle)
        public final String subtitle;

        public MyItem(String title, String subtitle) {
            this.title = title;
            this.subtitle = subtitle;
        }
    }
}
