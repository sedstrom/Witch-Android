package com.example.witch.animations;

import com.example.witch.R;

import android.widget.TextView;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;

class AnimationsViewModel {

    @BindTo(R.id.animations_fragment_text_1)
    final String one;

    @Binds
    final Binder<TextView, String> bindsOne;

    @BindTo(R.id.animations_fragment_text_2)
    final String two;

    @Binds
    final Binder<TextView, String> bindsTwo;

    @BindTo(R.id.animations_fragment_text_3)
    final String three;

    @Binds
    final Binder<TextView, String> bindsThree;

    @BindTo(R.id.animations_fragment_text_4)
    final String four;

    @Binds
    final Binder<TextView, String> bindsFour;

    AnimationsViewModel(String one, String two, String three, String four, Binder<TextView, String> binder) {
        this.one = one;
        this.bindsOne = binder;
        this.two = two;
        this.bindsTwo = binder;
        this.three = three;
        this.bindsThree = binder;
        this.four = four;
        this.bindsFour = binder;
    }
}
