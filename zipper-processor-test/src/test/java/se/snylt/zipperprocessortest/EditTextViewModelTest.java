package se.snylt.zipperprocessortest;


import android.view.View;
import android.widget.EditText;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class EditTextViewModelTest extends TestBase {

    @Override
    protected void testBind(final List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new EditTextViewModel(),
                new TestViewBinderUtils.VerifyPostBind<EditTextViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, EditTextViewModel viewModel, int viewId) {
                        switch (viewId) {
                            case 1:
                                verify((EditText)mockedView).setText(eq(viewModel.text));
                                break;
                            case 2:
                                verify((EditText)mockedView).setHint(eq(viewModel.hint));
                                break;
                        }
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new EditTextViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new EditTextViewModel_ViewBinder().createBinder();
    }
}