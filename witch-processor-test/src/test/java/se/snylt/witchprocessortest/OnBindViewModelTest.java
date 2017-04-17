package se.snylt.witchprocessortest;


import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.bindactions.TestOnBind;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class OnBindViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new OnBindViewModel(),
                new TestViewBinderUtils.VerifyPostBind<OnBindViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, OnBindViewModel viewModel, int viewId) {
                        switch (viewId) {
                            case 1 :
                                verify(mockedView).setContentDescription(eq(TestOnBind.class.getSimpleName()));
                                break;
                            case 2 :
                                verify(mockedView, times(3)).setContentDescription(eq(TestOnBind.class.getSimpleName()));
                                break;
                        }
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new OnBindViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new OnBindViewModel_ViewBinder().createBinder();
    }
}