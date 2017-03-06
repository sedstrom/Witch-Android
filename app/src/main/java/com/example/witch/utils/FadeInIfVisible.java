package com.example.witch.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPreBindAction;
import se.snylt.witch.viewbinder.bindaction.PreBindDone;

public class FadeInIfVisible implements OnPreBindAction<View, Integer>, OnPostBindAction<View, Integer> {

    @Override
    public void onPostBind(View view, Integer visibility) {
        // Fade in
        if(visibility == View.VISIBLE) {
            ObjectAnimator.ofFloat(view, View.ALPHA, 1f)
            .setDuration(600)
            .start();
        }
    }

    @Override
    public void onPreBind(View view, Integer visibility, final PreBindDone preBindDone) {
        if(visibility != View.VISIBLE) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(view, View.ALPHA, 0f).setDuration(300);
            alpha.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            preBindDone.done();
                        }
                    });
            alpha.start();
        } else {
            preBindDone.done();
        }
    }
}
