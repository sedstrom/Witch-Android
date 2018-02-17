package com.example.witch.recyclerview;

import com.example.witch.R;
import com.squareup.picasso.Picasso;

import android.view.View;
import android.widget.ImageView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class PostWithPicture extends Post {

    private final View.OnClickListener listener;

    private final String imageUrl;

    PostWithPicture(String title, String subtitle, View.OnClickListener listener, String imageUrl) {
        super(title, subtitle);
        this.listener = listener;
        this.imageUrl = imageUrl;
    }

    // Binder
    static class PostBinder extends RecyclerViewBinderAdapter.Binder<PostWithPicture> {

        PostBinder() {
            super(R.layout.recycler_view_item);
        }

        String title(){
            return item.title;
        }

        String subtitle() {
            return item.subtitle;
        }

        @BindData(id = R.id.my_item_container, view = View.class, set = "onClickListener")
        View.OnClickListener listener(){
            return item.listener;
        }

        String image() {
            return item.imageUrl;
        }

        @Bind
        Binder<ImageView, String> bindsImage = Binder.create(
                new SyncOnBind<ImageView, String>() {
                    @Override
                    public void onBind(ImageView imageView, String imageUrl) {
                        Picasso.with(imageView.getContext())
                                .load(imageUrl)
                                .into(imageView);
                    }
                });

        @Override
        public boolean bindsItem(Object item) {
            return item.getClass() == PostWithPicture.class;
        }
    }

}
