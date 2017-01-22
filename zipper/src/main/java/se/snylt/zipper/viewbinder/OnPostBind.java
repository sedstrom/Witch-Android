package se.snylt.zipper.viewbinder;

import android.view.View;

import se.snylt.zipper.BindAction;

public interface OnPostBind<V extends View, Value> extends BindAction {
    void onPostBind(V view, Value value);
}
