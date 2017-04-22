package com.example.witch;

import com.squareup.leakcanary.LeakCanary;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Application;
import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnBindListener;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);

        ObjectAnimator animator = ObjectAnimator.ofFloat(new View(this), View.ALPHA, 0f, 1f);
        animator.setDuration(300);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //
                OnBindListener l = null;
                l.onBindDone();
            }
        });
        animator.start();

    }
}
