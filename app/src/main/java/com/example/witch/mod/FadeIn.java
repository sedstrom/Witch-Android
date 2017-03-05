package com.example.witch.mod;

import android.animation.ObjectAnimator;
import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPreBindAction;
import se.snylt.witch.viewbinder.bindaction.PreBindDone;

public class FadeIn implements OnPostBindAction<View, Object>, OnPreBindAction<View, Object> {

    @Override
    public void onPostBind(View view, Object o) {
        ObjectAnimator.ofFloat(view, View.ALPHA, 1f).setDuration(650).start();
    }

    @Override
    public void onPreBind(View view, Object o, PreBindDone preBindDone) {
        view.setAlpha(0f);
        preBindDone.done();
    }
}
