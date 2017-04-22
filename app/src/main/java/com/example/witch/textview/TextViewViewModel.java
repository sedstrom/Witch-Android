package com.example.witch.textview;

import com.example.witch.R;

import se.snylt.witch.annotations.BindToEditText;
import se.snylt.witch.annotations.BindToTextView;

class TextViewViewModel {

    @BindToTextView(id = R.id.text_view_fragment_text_view)
    final String text;

    @BindToEditText(id = R.id.text_view_fragment_edit_text, set = "hint")
    final String hint;

    TextViewViewModel(String text, String hint) {
        this.text = text;
        this.hint = hint;
    }
}
