package com.example.witch.animations;

import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class SlideIn<Target extends View, Value> extends SyncOnBind<Target, Value> {

    @Override
    public void onBind(Target view, Value value) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -2000, 0f);
        animatorX.setDuration(300);
        animatorX.setInterpolator(new OvershootInterpolator(2f));
        animatorX.start();
    }

}
