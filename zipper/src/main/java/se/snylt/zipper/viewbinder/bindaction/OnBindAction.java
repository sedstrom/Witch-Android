package se.snylt.zipper.viewbinder.bindaction;

import android.view.View;

public interface OnBindAction<V extends View> extends BindAction {

    void onBind(V view, Object value);
}
