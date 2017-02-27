package se.snylt.zipperprocessortest;

import android.view.View;
import android.widget.CompoundButton;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class CompoundButtonViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new CompoundButtonViewModel(),
                new TestViewBinderUtils.VerifyPostBind<CompoundButtonViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, CompoundButtonViewModel viewModel, int viewId) {
                        switch (viewId) {
                            case 1 :
                                verify((CompoundButton)mockedView).setChecked(eq(viewModel.checked));
                                break;
                            case 2 :
                                verify((CompoundButton)mockedView).setButtonDrawable(eq(viewModel.buttonDrawable));
                                break;
                        }
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new CompoundButtonViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new CompoundButtonViewModel_ViewBinder().createBinder();
    }
}