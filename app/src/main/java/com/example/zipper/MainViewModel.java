package com.example.zipper;

import se.snylt.zipper.annotations.BindProperty;
import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.OnBind;
import se.snylt.zipper.viewbinder.onbind.ToastLongOnBind;

public class MainViewModel {

    @BindTo(R.id.main_activity_text)
    @BindProperty("text")
    public String one;

    @BindTo(R.id.main_activity_text_two)
    @BindProperty("text")
    public String two;

    @BindTo(R.id.main_activity_image)
    @BindProperty("imageResource")
    public int icon;

    @BindTo(R.id.main_activity_checkbox)
    @BindProperty("checked")
    public boolean checked;

    @BindTo(R.id.main_activity_radio_group)
    public int checkedPosition;

    @BindTo(R.id.main_activity_switch)
    @BindProperty("checked")
    public boolean switchChecked;

    @BindTo(R.id.activity_main)
    @OnBind(ToastLongOnBind.class)
    public final String welcomeMessage;

    public MainViewModel(String one, String two, int icon, boolean checked, int checkedPosition, boolean switchChecked, String welcomeMessage) {
        this.one = one;
        this.two = two;
        this.icon = icon;
        this.checked = checked;
        this.checkedPosition = checkedPosition;
        this.switchChecked = switchChecked;
        this.welcomeMessage = welcomeMessage;
    }
}