package com.example.witch.recyclerview;

import com.example.witch.R;

import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class Post {

    public final String title;

    public final String subtitle;

    Post(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    // Binder
    static class PostBinder extends RecyclerViewBinderAdapter.Binder<Post> {

        PostBinder() {super(R.layout.recycler_view_item_2);}

        String title() {
            return item.title;
        }

        String subtitle() {
            return item.subtitle;
        }

        @Override
        public boolean bindsItem(Object item) {
            return item.getClass() == Post.class;
        }
    }

}
