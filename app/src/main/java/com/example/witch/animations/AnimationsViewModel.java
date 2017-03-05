package com.example.witch.animations;

import com.example.witch.R;

import se.snylt.witch.annotations.BindToTextView;

public class AnimationsViewModel {

    @BindToTextView(id = R.id.animations_fragment_text_1)
    public final String one;

    @BindToTextView(id = R.id.animations_fragment_text_2)
    public final String two;

    @BindToTextView(id = R.id.animations_fragment_text_3)
    public final String three;

    @BindToTextView(id = R.id.animations_fragment_text_4)
    public final String four;

    public AnimationsViewModel(String one, String two, String three, String four) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
    }
}
