package se.snylt.zipper.viewbinder.utils;

import android.view.View;

import se.snylt.zipper.viewbinder.OnPostBind;

public class GoneIfNullOnPostBind implements OnPostBind<View, Object> {

    @Override
    public void onPostBind(View view, Object value) {
        if(value == null) {
            view.setVisibility(View.GONE);
        } else {
            view.setVisibility(View.VISIBLE);
        }
    }
}
