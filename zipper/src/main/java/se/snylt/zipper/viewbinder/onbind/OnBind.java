package se.snylt.zipper.viewbinder.onbind;

import android.view.View;

import se.snylt.zipper.BindAction;

public interface OnBind<V extends View> extends BindAction {

    void onBind(V view, Object value);
}
