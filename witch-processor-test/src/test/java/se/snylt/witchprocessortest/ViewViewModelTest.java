package se.snylt.witchprocessortest;

import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class ViewViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new ViewViewModel(),
                new TestViewBinderUtils.VerifyPostBind<ViewViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, ViewViewModel viewModel, int id) {
                        verify(mockedView).setVisibility(eq((int)viewModel.visibility));
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new ViewViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new ViewViewModel_ViewBinder().createBinder();
    }
}