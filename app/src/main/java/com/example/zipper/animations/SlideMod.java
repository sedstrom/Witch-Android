package com.example.zipper.animations;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

public class SlideMod implements OnPreBindAction<View, Object>, OnPostBindAction<View, Object> {

    int count = 0;

    @Override
    public void onPreBind(View view, Object o, final PreBindDone preBindDone) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.getWidth());
        animatorX.setDuration(300);
        animatorX.setStartDelay(count*80);
        animatorX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                preBindDone.done();
            }
        });
        animatorX.start();
        count ++;
    }

    @Override
    public void onPostBind(View view, Object o) {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -view.getWidth(), 0f);
        animatorX.setDuration(300);
        animatorX.setInterpolator(new OvershootInterpolator(2f));
        animatorX.start();
    }
}
