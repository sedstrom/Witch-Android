package com.example.witch.responsemodel;

import com.example.witch.R;

import android.view.View;

import se.snylt.witch.annotations.BindData;

public class ResponseViewModel {

    // Maps to response
    public String avatar_url;

    public final String hint = "Github username";

    private  Integer progressBarVisibility;

    @BindData(id = R.id.profile_picture_progress, view = View.class, set="visibility")
    public Integer getProgressVisibility() {
        return progressBarVisibility;
    }

    private String error;

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
