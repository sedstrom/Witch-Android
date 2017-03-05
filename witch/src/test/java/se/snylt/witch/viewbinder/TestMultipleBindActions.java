package se.snylt.witch.viewbinder;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPreBindAction;
import se.snylt.witch.viewbinder.bindaction.PreBindDone;

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
