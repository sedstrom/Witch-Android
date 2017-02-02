package se.snylt.zipper.viewbinder.bindaction;

import android.view.View;

public interface OnPostBindAction<V extends View, Value> extends BindAction {
    void onPostBind(V view, Value value);
}
