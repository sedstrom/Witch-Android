package com.example.witch;


import android.widget.TextView;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class DataBinder extends RecyclerViewBinderAdapter.Binder<ApiData> {

    DataBinder() { super(R.layout.data_list_item); }

    @BindData(id = R.id.item_title, view = TextView.class, set = "text")
    String title() {
        return item.description + ": " + item.id;
    }

    @Override
    public boolean bindsItem(Object item) {
        return item instanceof ApiData;
    }
}
