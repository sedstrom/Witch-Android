package com.example.witch.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class CustomView extends android.support.v7.widget.AppCompatTextView {

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context) {
        super(context);
    }

    public void setMyData(String data) {
        setText(data);
    }

}
