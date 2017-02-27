package se.snylt.zipperprocessortest.bindactions;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnBindAction;

public class TestOnBindAction implements OnBindAction {

    @Override
    public void onBind(View view, Object o) {
        view.setContentDescription(TestOnBindAction.class.getSimpleName());
    }
}
