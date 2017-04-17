package com.example.witch.animations;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class FlipIn<Target extends View, Value> extends SyncOnBind<Target, Value> {

    @Override
    public void onBind(Target view, Value o) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.ROTATION, -10f, 0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, View.ROTATION_X, -90f, 0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.setInterpolator(new OvershootInterpolator(2f));
        set.playTogether(animatorX, animatorY);
        set.start();
    }
}
