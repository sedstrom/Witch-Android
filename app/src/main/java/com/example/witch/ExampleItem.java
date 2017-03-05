package com.example.witch;

import android.view.View;

import se.snylt.witch.annotations.BindToTextView;
import se.snylt.witch.annotations.BindToView;

public class ExampleItem {

    @BindToView(id = R.id.example_list_item_container, view = View.class, set = "onClickListener")
    public final View.OnClickListener clickListener;

    @BindToTextView(id=R.id.example_list_item_title)
    public final String title;

    @BindToTextView(id=R.id.example_list_item_subtitle)
    public final String subTitle;

    public ExampleItem(View.OnClickListener clickListener, String title, String subTitle) {
        this.clickListener = clickListener;
        this.title = title;
        this.subTitle = subTitle;
    }
}
