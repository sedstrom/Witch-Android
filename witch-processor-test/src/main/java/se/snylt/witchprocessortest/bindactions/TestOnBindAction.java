package se.snylt.witchprocessortest.bindactions;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnBindAction;

public class TestOnBindAction implements OnBindAction {

    @Override
    public void onBind(View view, Object o) {
        view.setContentDescription(TestOnBindAction.class.getSimpleName());
    }
}
