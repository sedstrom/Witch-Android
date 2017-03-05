package se.snylt.witchprocessortest.bindactions;

import android.view.View;

import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;

public class TestOnPostBindAction implements OnPostBindAction {

    @Override
    public void onPostBind(View view, Object o) {
        view.setContentDescription(TestOnPostBindAction.class.getSimpleName());
    }
}
