package se.snylt.witchprocessortest;

import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class ValueBinderViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, final Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new ValueBinderViewModel(),
                new TestViewBinderUtils.VerifyPostBind<ValueBinderViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, ValueBinderViewModel viewModel, int id) {
                        verify(mockedView).setVisibility(eq(viewModel.VISIBILITY));
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new ValueBinderViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new ValueBinderViewModel_ViewBinder().createBinder();
    }
}