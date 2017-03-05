package com.example.witch.recyclerview;

import com.example.witch.R;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import se.snylt.witch.annotations.BindToTextView;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.Witch;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<MyItem> items;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Witch.bind(items.get(position), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<MyItem> items) {
        this.items = items;
    }

    public static class MyItem {

        @BindToTextView(id = R.id.my_item_title)
        public final String title;

        @BindToTextView(id = R.id.my_item_subtitle)
        public final String subtitle;

        @BindToView(id = R.id.my_item_container, view = View.class, set = "onClickListener")
        public final View.OnClickListener listener;

        public MyItem(String title, String subtitle, View.OnClickListener listener) {
            this.title = title;
            this.subtitle = subtitle;
            this.listener = listener;
        }
    }
}
