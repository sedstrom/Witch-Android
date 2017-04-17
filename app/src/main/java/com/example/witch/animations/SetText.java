package com.example.witch.animations;

import android.widget.TextView;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;


class SetText extends SyncOnBind<TextView,String> {

    @Override
    public void onBind(TextView textView, String s) {
        textView.setText(s);
    }
}
