package com.example.witch.mod;

import com.example.witch.R;

import se.snylt.witch.annotations.BindToTextView;

public class ViewModel {

    @BindToTextView(id = R.id.mods_fragment_text)
    public String text;
}
