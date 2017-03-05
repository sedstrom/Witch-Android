package com.example.witch.picasso;

import com.example.witch.R;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.OnBind;

public class PicassoViewModel {

    @BindTo(R.id.picasso_fragment_imageview)
    @OnBind(PicassoOnBind.class)
    public final String url;

    public PicassoViewModel(String url) {
        this.url = url;
    }
}
