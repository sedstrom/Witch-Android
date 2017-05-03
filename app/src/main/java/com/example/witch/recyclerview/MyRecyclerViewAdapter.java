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

class MyRecyclerViewAdapter extends RecyclerView.Adapter {

    private List<MyItem> items;

    private final BindMyItem bindMyItem = new BindMyItem();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SimpleViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Witch.bind(bindMyItem.use(items.get(position)), holder.itemView);
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public void setItems(List<MyItem> items) {
        this.items = items;
    }

    // Pure view data model
    static class MyItem {

        public final String title;

        public final String subtitle;

        final View.OnClickListener listener;

        MyItem(String title, String subtitle, View.OnClickListener listener) {
            this.title = title;
            this.subtitle = subtitle;
            this.listener = listener;
        }
    }

    // Separate class for binding data
    class BindMyItem {
        private MyItem item;

        BindMyItem use(MyItem item) {
            this.item = item;
            return this;
        }

        @BindToTextView(id = R.id.my_item_title)
        String title(){
            return item.title;
        }

        @BindToTextView(id = R.id.my_item_subtitle)
        String subtitle() {
            return item.subtitle;
        }

        @BindToView(id = R.id.my_item_container, view = View.class, set = "onClickListener")
        View.OnClickListener listener(){
            return item.listener;
        }
    }
}
