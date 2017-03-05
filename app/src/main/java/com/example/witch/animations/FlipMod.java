package com.example.witch.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;

import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPreBindAction;
import se.snylt.witch.viewbinder.bindaction.PreBindDone;

public class FlipMod implements OnPreBindAction<View, Object>, OnPostBindAction<View, Object> {

    int count = 0;

    @Override
    public void onPreBind(View view, Object o, final PreBindDone preBindDone) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.ROTATION, 0f, 10f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, View.ROTATION_X, 0f, 90f);
        AnimatorSet set = new AnimatorSet();
        set.setInterpolator(new AccelerateInterpolator(2f));
        set.setDuration(300);
        set.setStartDelay(count*80);
        set.playTogether(animatorX, animatorY);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                preBindDone.done();
            }
        });
        set.start();
        count ++;
    }

    @Override
    public void onPostBind(View view, Object o) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.ROTATION, -10f, 0f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(view, View.ROTATION_X, -90f, 0f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300);
        set.setInterpolator(new OvershootInterpolator(2f));
        set.playTogether(animatorX, animatorY);
        set.start();
    }
}
