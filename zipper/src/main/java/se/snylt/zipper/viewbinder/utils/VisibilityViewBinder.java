package se.snylt.zipper.viewbinder.utils;

import android.view.View;

import se.snylt.zipper.viewbinder.onbind.OnBind;

public class VisibilityViewBinder implements OnBind<View> {

    @Override
    public void onBind(View view, Object value) {
        view.setVisibility((Integer) value);
    }
}
