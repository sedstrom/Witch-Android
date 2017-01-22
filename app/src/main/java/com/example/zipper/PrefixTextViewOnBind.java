package com.example.zipper;


import android.widget.TextView;

import se.snylt.zipper.viewbinder.onbind.OnBind;

public class PrefixTextViewOnBind implements OnBind<TextView> {

    @Override
    public void onBind(TextView view, Object value) {
        view.setText("Address: " + value);
    }
}
