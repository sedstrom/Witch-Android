package com.example.zipper.textview;

import com.example.zipper.R;

import se.snylt.zipper.annotations.BindToEditText;
import se.snylt.zipper.annotations.BindToTextView;

import static se.snylt.zipper.annotations.ViewProperties.EditText.HINT;

public class TextViewViewModel {

    @BindToTextView(id = R.id.text_view_fragment_text_view)
    public final String text;

    @BindToEditText(id = R.id.text_view_fragment_edit_text, set = HINT)
    public final String hint;

    public TextViewViewModel(String text, String hint) {
        this.text = text;
        this.hint = hint;
    }
}
