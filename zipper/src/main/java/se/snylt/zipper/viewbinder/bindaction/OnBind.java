package se.snylt.zipper.viewbinder.bindaction;

import android.view.View;

public interface OnBind<V extends View> extends BindAction {

    void onBind(V view, Object value);
}
