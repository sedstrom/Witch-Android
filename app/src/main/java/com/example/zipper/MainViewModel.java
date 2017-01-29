package com.example.zipper;

import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToImageView;
import se.snylt.zipper.annotations.BindToRecyclerView;
import se.snylt.zipper.annotations.BindToTextView;

import static se.snylt.zipper.annotations.ViewProperties.EditText.HINT;

public class MainViewModel {

    // View and default bind property
    @BindToTextView(id = R.id.main_activity_text_view)
    final public String text;

    // Override default bind property
    @BindToEditText(id = R.id.main_activity_edit_text, set = HINT)
    final public String hint;

    @BindToImageView(id = R.id.main_activity_image_view)
    final public int image;

    // RecyclerView
    @BindToRecyclerView(id = R.id.main_activity_recycler_view, adapter = MyRecyclerViewAdapter.class, set = "items")
    public final MyRecyclerViewAdapter.MyItem[] items;

    public MainViewModel(String text, String hint, int image, MyRecyclerViewAdapter.MyItem[] items) {
        this.text = text;
        this.hint = hint;
        this.image = image;
        this.items = items;
    }
}