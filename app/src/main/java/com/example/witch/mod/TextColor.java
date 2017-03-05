package com.example.witch.mod;

import android.widget.TextView;

import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;

class TextColor implements OnPostBindAction<TextView, Object> {

    private final int color;

    public TextColor(int color) {
        this.color = color;
    }

    @Override
    public void onPostBind(TextView view, Object o) {
        view.setTextColor(color);
    }
}
