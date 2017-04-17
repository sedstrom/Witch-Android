package com.example.witch.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import se.snylt.witch.viewbinder.bindaction.AsyncOnBind;
import se.snylt.witch.viewbinder.bindaction.OnBindListener;

class SlideOut<Target extends View, Value> extends AsyncOnBind<Target, Value> {

    private int count = 0;

    @Override
    public void onBind(Target view, Value value, final OnBindListener onBindListener) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.getWidth());
        animatorX.setDuration(300);
        animatorX.setStartDelay(count*80);
        animatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onBindListener.onBindDone();
            }
        });
        animatorX.start();
        count ++;
    }


}
