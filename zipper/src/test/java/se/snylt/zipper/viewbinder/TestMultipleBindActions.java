package se.snylt.zipper.viewbinder;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPostBindAction;
import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

public class TestMultipleBindActions implements OnPreBindAction, OnBindAction, OnPostBindAction {

    @Override
    public void onBind(View view, Object o) {

    }

    @Override
    public void onPostBind(View view, Object o) {

    }

    @Override
    public void onPreBind(View view, Object o, PreBindDone preBindDone) {

    }
}
