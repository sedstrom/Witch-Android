package com.example.zipper.utils;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;

public class InvisibleIfNull implements OnPostBindAction<View, Object> {

    @Override
    public void onPostBind(View view, Object value) {
        view.setVisibility(value == null ? View.INVISIBLE : View.VISIBLE);
    }
}
