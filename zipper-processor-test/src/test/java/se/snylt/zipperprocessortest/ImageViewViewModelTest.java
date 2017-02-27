package se.snylt.zipperprocessortest;


import android.view.View;
import android.widget.ImageView;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class ImageViewViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new ImageViewViewModel(),
                new TestViewBinderUtils.VerifyPostBind<ImageViewViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, ImageViewViewModel viewModel, int viewId) {
                        switch (viewId) {
                            case 1:
                                verify((ImageView)mockedView).setImageResource(eq(viewModel.imageId));
                                break;
                            case 2:
                                verify((ImageView)mockedView).setContentDescription(eq(viewModel.contentDescription));
                                break;
                        }
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new ImageViewViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new ImageViewViewModel_ViewBinder().createBinder();
    }
}