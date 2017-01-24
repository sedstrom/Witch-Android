package com.example.zipper;

import se.snylt.zipper.annotations.BindTo;
import se.snylt.zipper.annotations.BindToCompoundButton;
import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToImageView;
import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.annotations.OnBind;
import se.snylt.zipper.viewbinder.onbind.ToastLongOnBind;

import static se.snylt.zipper.ViewProperties.EditText.HINT;

public class MainViewModel {

    @BindToTextView(id = R.id.main_activity_text_view) // view + bind action
    final public String text;

    @BindToEditText(id = R.id.main_activity_edit_text, set = HINT) // override default
    final public String hint;

    @BindToImageView(id = R.id.main_activity_image_view)
    final public int image;

    @BindToCompoundButton(id = R.id.main_activity_check_box)
    final public boolean checked;

    @BindTo(R.id.activity_main) // view
    @OnBind(ToastLongOnBind.class) // bind action
    final public String welcomeMessage;

    public MainViewModel(String text, String hint, int image, boolean checked, String welcomeMessage) {
        this.text = text;
        this.hint = hint;
        this.image = image;
        this.checked = checked;
        this.welcomeMessage = welcomeMessage;
    }
}