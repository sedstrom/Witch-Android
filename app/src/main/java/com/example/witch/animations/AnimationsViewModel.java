package com.example.witch.animations;

import com.example.witch.R;

import android.widget.TextView;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

class AnimationsViewModel {

    private final String one;

    private final String two;

    private final String three;

    private final String four;

    private final Binder<TextView, String> binder;

    AnimationsViewModel(String one, String two, String three, String four, Binder<TextView, String> binder) {
        this.one = one;
        this.two = two;
        this.three = three;
        this.four = four;
        this.binder = binder;
    }

    @BindTo(R.id.animations_fragment_text_1)
    ValueBinder<TextView, String> one() {
        return ValueBinder.create(one, binder);
    }

    @BindTo(R.id.animations_fragment_text_2)
    ValueBinder<TextView, String> two() {
        return ValueBinder.create(two, binder);
    }

    @BindTo(R.id.animations_fragment_text_3)
    ValueBinder<TextView, String> three() {
        return ValueBinder.create(three, binder);
    }

    @BindTo(R.id.animations_fragment_text_4)
    ValueBinder<TextView, String> four() {
        return ValueBinder.create(four, binder);
    }
}
