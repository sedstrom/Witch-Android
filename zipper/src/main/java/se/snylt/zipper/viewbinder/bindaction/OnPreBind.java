package se.snylt.zipper.viewbinder.bindaction;

import android.view.View;

public interface OnPreBind<V extends View, Value> extends BindAction {
    void onPreBind(V view, Value value);
}
