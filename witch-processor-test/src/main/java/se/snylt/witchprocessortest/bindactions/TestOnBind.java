package se.snylt.witchprocessortest.bindactions;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

public class TestOnBind extends SyncOnBind<View, Object> {

    @Override
    public void onBind(View view, Object o) {
        view.setContentDescription(TestOnBind.class.getSimpleName());
    }

}
