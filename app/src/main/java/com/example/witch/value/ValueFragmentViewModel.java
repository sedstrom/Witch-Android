package com.example.witch.value;

/**
 * Created by sied on 2017-04-15.
 */


import com.example.witch.R;

import android.widget.TextView;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

class ValueFragmentViewModel {

    private final String text;

    ValueFragmentViewModel(String text) {
        this.text = text;
    }

    @BindTo(R.id.value_view_fragment_text)
    public ValueBinder<TextView, String> text() {
        return ValueBinder.create(text, Binder.create(new SyncOnBind<TextView, String>() {
            @Override
            public void onBind(TextView textView, String s) {
                textView.setText(s + "(" + s.length() + ")");
            }
        }));
    }
}

