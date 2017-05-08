package com.example.witch;

import android.view.View;

import se.snylt.witch.annotations.BindToTextView;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class ExampleItem {

    final View.OnClickListener clickListener;

    final String title;

    final String subTitle;

    ExampleItem(View.OnClickListener clickListener, String title, String subTitle) {
        this.clickListener = clickListener;
        this.title = title;
        this.subTitle = subTitle;
    }

    static class Binder extends RecyclerViewBinderAdapter.Binder<ExampleItem> {

        protected Binder() {
            super(R.layout.example_list_item);
        }

        @BindToView(id = R.id.example_list_item_container, view = View.class, set = "onClickListener")
        final View.OnClickListener clickListener() {
            return item.clickListener;
        }

        @BindToTextView(id=R.id.example_list_item_title)
        final String title() {
            return item.title;
        }

        @BindToTextView(id=R.id.example_list_item_subtitle)
        final String subTitle() {
            return item.subTitle;
        }

        @Override
        public Class getItemType() {
            return ExampleItem.class;
        }
    }
}