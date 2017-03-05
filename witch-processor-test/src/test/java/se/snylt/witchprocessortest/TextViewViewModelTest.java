package se.snylt.witchprocessortest;


import android.view.View;
import android.widget.TextView;

import java.util.List;

import se.snylt.witch.viewbinder.Binder;
import se.snylt.witch.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class TextViewViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> viewBinders, Object viewHolder) {
        verifyViewBinder(viewBinders, viewHolder, new TextViewViewModel(),
                new TestViewBinderUtils.VerifyPostBind<TextViewViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, TextViewViewModel model, int id) {
                        switch (id) {
                            case 1:
                                verify((TextView)mockedView).setText(eq(model.text));
                                break;
                            case 2:
                                verify((TextView)mockedView).setContentDescription(eq(model.contentDescription));
                                break;
                        }
                    }
                });
    }

    @Override
    protected Object getViewHolder() {
        return new TextViewViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new TextViewViewModel_ViewBinder().createBinder();
    }
}