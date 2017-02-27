package se.snylt.zipperprocessortest.bindactions;

import android.view.View;

import se.snylt.zipper.viewbinder.bindaction.OnPreBindAction;
import se.snylt.zipper.viewbinder.bindaction.PreBindDone;

public class TestOnPreBindAction implements OnPreBindAction {

    @Override
    public void onPreBind(View view, Object o, PreBindDone preBindDone) {
        view.setContentDescription(TestOnPreBindAction.class.getSimpleName());
    }
}
