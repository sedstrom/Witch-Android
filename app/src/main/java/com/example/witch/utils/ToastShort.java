package com.example.witch.utils;

import android.view.View;
import android.widget.Toast;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class ToastShort extends SyncOnBind<View,String> {

    @Override
    public void onBind(View view, String s) {
        if(s != null) {
            Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
