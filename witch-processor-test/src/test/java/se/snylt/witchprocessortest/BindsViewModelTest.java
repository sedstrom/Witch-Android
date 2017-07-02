package se.snylt.witchprocessortest;


import org.junit.Test;

import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BindsViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        // NA
    }

    @Test
    public void binds_ShouldBind() {
        BindsViewModel model = new BindsViewModel();
        TargetViewBinder viewBinder = getTargetViewBinder();
        doBind(viewBinder, model, verifyPostBindSetTag());
    }

    private TestViewBinderUtils.VerifyPostBind<BindsViewModel> verifyPostBindSetTag() {
        return new TestViewBinderUtils.VerifyPostBind<BindsViewModel>() {
            @Override
            public void onPostBind(View mockedView, BindsViewModel viewModel, int viewId) {
                if(viewId == 0) {
                    verify(mockedView, times(1)).setTag(eq("field"));
                } else if(viewId == 1) {
                    verify(mockedView, times(1)).setTag(eq("method"));
                } else if(viewId == 2) {
                    verify(mockedView, times(1)).setTag(eq("foo"));
                } else if(viewId == 3) {
                    verify(mockedView, times(1)).setTag(eq("bar"));
                } else if(viewId == 4) {
                    verify(mockedView, times(1)).setTag(eq("poo"));
                }
            }
        };
    }

    @Override
    protected Object getViewHolder() {
        return new BindsViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new BindsViewModel_ViewBinder().createBinder();
    }
}