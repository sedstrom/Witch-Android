package com.example.witch.picasso;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import android.widget.ImageView;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class PicassoOnBind extends SyncOnBind<ImageView,String> {

    @Override
    public void onBind(ImageView view, String url) {
        Picasso.with(view.getContext())
                .load(url)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .into(view);
    }
}
