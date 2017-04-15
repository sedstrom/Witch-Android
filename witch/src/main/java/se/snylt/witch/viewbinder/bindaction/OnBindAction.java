package se.snylt.witch.viewbinder.bindaction;

import android.view.View;

public interface OnBindAction<V extends View, Value> extends BindAction<Value> {

    void onBind(V view, Value value);
}
