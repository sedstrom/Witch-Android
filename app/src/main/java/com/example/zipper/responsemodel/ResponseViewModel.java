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

    // Maps to response
    @BindTo(R.id.profile_picture)
    @OnBind(PicassoOnBind.class)
    public String avatar_url;

    @BindToEditText(id = R.id.response_model_fragment_edit_text, set = "hint")
    public final String hint = "Github username";

    private  Integer progressBarVisibility;

    @BindToView(id = R.id.profile_picture_progress, view = View.class, set="visibility")
    public Integer getProgressVisibility() {
        return progressBarVisibility;
    }

    private String error;

    @BindToTextView(id = R.id.profile_picture_error)
    @OnBind(InvisibleIfNull.class)
    public String getError() {
        return error;
    }

    public ResponseViewModel(String error, Integer progressBarVisibility) {
        this.error = error;
        this.progressBarVisibility = progressBarVisibility;
    }

    public ResponseViewModel(){
        this(null, View.GONE);
    }

    public static ResponseViewModel error(String error) {
        return new ResponseViewModel(error, View.GONE);
    }

    public static ResponseViewModel progress() {
        return new ResponseViewModel(null, View.VISIBLE);
    }

    public static ResponseViewModel idle() {
        return new ResponseViewModel();
    }
}
