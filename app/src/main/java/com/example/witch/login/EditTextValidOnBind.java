package com.example.witch.login;

import com.example.witch.R;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.widget.EditText;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class EditTextValidOnBind extends SyncOnBind<EditText,Boolean> {

    @Override
    public void onBind(EditText view, Boolean isValid) {
        if(isValid) {
            ColorStateList colorStateList = ColorStateList.valueOf(ContextCompat.getColor(view.getContext(), R.color.colorAccent));
            ViewCompat.setBackgroundTintList(view, colorStateList);
        } else {
            ColorStateList colorStateList = ColorStateList.valueOf(Color.RED);
            ViewCompat.setBackgroundTintList(view, colorStateList);
        }
    }
}
