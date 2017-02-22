package com.example.zipper.picasso;

import com.example.zipper.R;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.OnBind;

public class PicassoViewModel {

    @BindTo(R.id.picasso_fragment_imageview)
    @OnBind(PicassoOnBind.class)
    public final String url;

    public PicassoViewModel(String url) {
        this.url = url;
    }
}
