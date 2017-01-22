package se.snylt.zipper.viewbinder.utils;

import android.view.View;

import se.snylt.zipper.viewbinder.OnPostBind;

public class SlideUpOnPostBind implements OnPostBind<View, Object> {

    @Override
    public void onPostBind(View view, Object ignored) {
        view.setTranslationY(500);
        view.animate().translationY(0).setStartDelay(500).setDuration(2000).start();
    }
}
