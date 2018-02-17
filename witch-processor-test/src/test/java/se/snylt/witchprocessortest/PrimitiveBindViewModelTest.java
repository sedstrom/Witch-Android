/*
package se.snylt.witchprocessortest;


import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class PrimitiveBindViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new PrimitiveBindViewModel(),
                new TestViewBinderUtils.VerifyPostBind<PrimitiveBindViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, PrimitiveBindViewModel viewModel, int viewId) {
                        switch (viewId) {
                            case 1 :
                                verify(mockedView).setTag(eq(1));
                                break;
                            case 2 :
                                verify(mockedView).setTag(eq((float)1));
                                break;
                            case 3 :
                                verify(mockedView).setTag(eq((double)1));
                                break;
                            case 4 :
                                verify(mockedView).setTag(eq((byte)0x01));
                                break;
                            case 5 :
                                verify(mockedView).setTag(eq((long)1));
                                break;
                            case 6 :
                                verify(mockedView).setTag(eq((short)1));
                                break;
                            case 7 :
                                verify(mockedView).setTag(eq((char)1));
                                break;
                            case 8 :
                                verify(mockedView).setTag(eq(true));
                                break;
                        }
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new PrimitiveBindViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new PrimitiveBindViewModel_ViewBinder().createBinder();
    }
}*/
