package com.example.witch.utils;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class InvisibleIfNull extends SyncOnBind<View, Object> {

    @Override
    public void onBind(View view, Object value) {
        view.setVisibility(value == null ? View.INVISIBLE : View.VISIBLE);
    }
}
