package com.example.zipper.responsemodel;

import com.example.zipper.R;
import com.example.zipper.picasso.PicassoOnBind;
import com.example.zipper.utils.InvisibleIfNull;

import android.view.View;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.annotations.BindToView;
import se.snylt.zipper.annotations.OnBind;

public class ResponseViewModel {

    @BindToEditText(id = R.id.response_model_fragment_edit_text, set = "hint")
    public final String hint = "Github username";

    @BindTo(R.id.profile_picture)
    @OnBind(PicassoOnBind.class)
    public String avatar_url; // Maps to response model

    @BindToView(id = R.id.profile_picture_progress, view = View.class, set="visibility")
    public Integer progressBarVisibility = View.GONE;

    @BindToTextView(id = R.id.profile_picture_error)
    @OnBind(InvisibleIfNull.class)
    public String error;
}
