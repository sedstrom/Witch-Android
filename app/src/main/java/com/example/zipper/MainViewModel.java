package com.example.zipper;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToImageView;
import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.annotations.OnBind;
import se.snylt.zipper.viewbinder.onbind.ToastLongOnBind;

public class MainViewModel {

    @BindToTextView(id = R.id.main_activity_text)
    public String one;

    @BindToTextView(id = R.id.main_activity_text_two)
    public int two;

    @BindToImageView(id = R.id.main_activity_icon)
    public int icon;

    @BindTo(R.id.activity_main)
    @OnBind(ToastLongOnBind.class)
    public final String welcomeMessage;

    public MainViewModel(String one, int two, int icon, String welcomeMessage) {
        this.one = one;
        this.two = two;
        this.icon = icon;
        this.welcomeMessage = welcomeMessage;
    }
}