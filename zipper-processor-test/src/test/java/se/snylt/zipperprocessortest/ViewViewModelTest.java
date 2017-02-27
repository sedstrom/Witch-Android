package se.snylt.zipperprocessortest;

import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

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
    protected Binder getBinder() {
        return new ViewViewModel_ViewBinder().createBinder();
    }
}