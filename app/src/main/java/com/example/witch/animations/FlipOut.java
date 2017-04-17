package com.example.witch.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import se.snylt.witch.viewbinder.bindaction.AsyncOnBind;
import se.snylt.witch.viewbinder.bindaction.OnBindListener;

class FlipOut<Target extends View, Value> extends AsyncOnBind<Target, Value> {

    int count;

    @Override
    public void onBind(Target view, Value o, final OnBindListener onBindListener) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 10f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, View.ROTATION_X, 0f, 90f);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateInterpolator(2f));
        set.setDuration(300);
        set.setStartDelay(80 * count);
        set.playTogether(animatorX, animatorY);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                onBindListener.onBindDone();
            }
        });
        set.start();
        count++;
    }
}
