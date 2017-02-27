package se.snylt.zipperprocessortest;


import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.bindactions.TestOnBindAction;
import se.snylt.zipperprocessortest.bindactions.TestOnPostBindAction;
import se.snylt.zipperprocessortest.bindactions.TestOnPreBindAction;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

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
                                verify(mockedView).setContentDescription(eq(TestOnBindAction.class.getSimpleName()));
                                break;
                            case 2 :
                                verify(mockedView).setContentDescription(eq(TestOnPreBindAction.class.getSimpleName()));
                                verify(mockedView).setContentDescription(eq(TestOnBindAction.class.getSimpleName()));
                                verify(mockedView).setContentDescription(eq(TestOnPostBindAction.class.getSimpleName()));
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
    protected Binder getBinder() {
        return new OnBindViewModel_ViewBinder().createBinder();
    }
}