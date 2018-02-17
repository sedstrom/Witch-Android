package com.example.witch.animations;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.viewbinder.bindaction.Binder;

class AnimationsViewModel {

    final String one;

    @Bind
    final Binder<TextView, String> bindsOne;

    final String two;

    @Bind
    final Binder<TextView, String> bindsTwo;

    final String three;

    @Bind
    final Binder<TextView, String> bindsThree;

    final String four;

    @Bind
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
