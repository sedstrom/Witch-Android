package com.example.zipper;

import android.widget.Switch;

import se.snylt.zipper.annotations.BindToCompoundButton;
import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToImageView;
import se.snylt.zipper.annotations.BindToRecyclerView;
import se.snylt.zipper.annotations.BindToTextView;
import se.snylt.zipper.annotations.BindToView;

import static se.snylt.zipper.ViewProperties.CompoundButton.CHECKED;
import static se.snylt.zipper.ViewProperties.EditText.HINT;
import static se.snylt.zipper.ViewProperties.TextView.TEXT;

public class MainViewModel {

    @BindToTextView(id = R.id.main_activity_text_view) // view + bind action
    final public String text;

    @BindToEditText(id = R.id.main_activity_edit_text, set = HINT) // override default
    final public String hint;

    @BindToImageView(id = R.id.main_activity_image_view)
    final public int image;

    @BindToCompoundButton(id = R.id.main_activity_check_box)
    final public boolean checked;

    @BindToView(view = Switch.class, id = R.id.main_activity_switch, set = CHECKED) // Bind to view not supported by annotation
    final public boolean switched;

    @BindToView(view = Switch.class, id = R.id.main_activity_switch, set = TEXT)
    final public String switchText;

    @BindToRecyclerView(
            id = R.id.main_activity_recycler_view,
            adapter = MyRecyclerViewAdapter.class,
            set = "items")
    public final MyRecyclerViewAdapter.MyItem[] items;

    public MainViewModel(String text, String hint, int image, boolean checked, String switchText, boolean switched, MyRecyclerViewAdapter.MyItem[] items) {
        this.text = text;
        this.hint = hint;
        this.image = image;
        this.checked = checked;
        this.switchText = switchText;
        this.switched = switched;
        this.items = items;
    }
}