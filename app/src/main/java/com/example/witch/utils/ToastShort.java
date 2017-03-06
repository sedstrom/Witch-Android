package com.example.witch.utils;

import android.view.View;
import android.widget.Toast;

import se.snylt.witch.viewbinder.bindaction.OnBindAction;

public class ToastShort implements OnBindAction<View, String> {

    @Override
    public void onBind(View view, String s) {
        if(s != null) {
            Toast.makeText(view.getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}
