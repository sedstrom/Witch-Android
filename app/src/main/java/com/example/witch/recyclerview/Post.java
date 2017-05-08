package com.example.witch.recyclerview;

import com.example.witch.R;

import se.snylt.witch.annotations.BindToTextView;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class Post {

    public final String title;

    public final String subtitle;

    Post(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    // Binder
    static class Binder extends RecyclerViewBinderAdapter.Binder<Post> {

        Binder() {super(R.layout.recycler_view_item_2);}

        @BindToTextView(id = R.id.my_item_title)
        String title() {
            return item.title;
        }

        @BindToTextView(id = R.id.my_item_subtitle)
        String subtitle() {
            return item.subtitle;
        }

        @Override
        public Class getItemType() {
            return Post.class;
        }
    }

}
