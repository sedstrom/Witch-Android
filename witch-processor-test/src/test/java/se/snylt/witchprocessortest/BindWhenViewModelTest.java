package se.snylt.witchprocessortest;


import org.junit.Test;

import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BindWhenViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        // NA
    }

    @Test
    public void bindWhen_Always() {
        String value = "foo";
        BindWhenViewModel model = new BindWhenViewModel();
        TargetViewBinder viewBinder = getTargetViewBinder();
        model.always = value;
        doBind(viewBinder, model, verifyPostBindContentDescription(1, android.R.id.button1, value));
        doBind(viewBinder, model, verifyPostBindContentDescription(1, android.R.id.button1, value));
    }

    @Test
    public void bindWhen_NotEquals() {
        BindWhenViewModel model = new BindWhenViewModel();
        TargetViewBinder viewBinder = getTargetViewBinder();
        model.notEquals = "foo";
        doBind(viewBinder, model, verifyPostBindContentDescription(1, android.R.id.button3, "foo"));
        model.notEquals = "foo";
        doBind(viewBinder, model, verifyPostBindContentDescription(0, android.R.id.button3, "foo"));
        model.notEquals = "bar";
        doBind(viewBinder, model, verifyPostBindContentDescription(1, android.R.id.button3, "bar"));
    }

    @Test
    public void bindWhen_NotSame() {
        AlwaysEquals foo = new AlwaysEquals();
        AlwaysEquals equalsFoo = new AlwaysEquals();
        TargetViewBinder viewBinder = getTargetViewBinder();
        BindWhenViewModel model = new BindWhenViewModel();
        model.notSame = foo;
        doBind(viewBinder, model, verifyPostBindSetTag(1, android.R.id.button2, foo));
        model.notSame = equalsFoo;
        doBind(viewBinder, model, verifyPostBindSetTag(1, android.R.id.button2, equalsFoo));
    }

    private TestViewBinderUtils.VerifyPostBind<BindWhenViewModel> verifyPostBindContentDescription(final int times, final int id, final String value) {
        return new TestViewBinderUtils.VerifyPostBind<BindWhenViewModel>() {
            @Override
            public void onPostBind(View mockedView, BindWhenViewModel viewModel, int viewId) {
                if(viewId == id) {
                    verify(mockedView, times(times)).setContentDescription(eq(value));
                }
            }
        };
    }

    private TestViewBinderUtils.VerifyPostBind<BindWhenViewModel> verifyPostBindSetTag(final int times, final int id, final Object value) {
        return new TestViewBinderUtils.VerifyPostBind<BindWhenViewModel>() {
            @Override
            public void onPostBind(View mockedView, BindWhenViewModel viewModel, int viewId) {
                if(viewId == id) {
                    verify(mockedView, times(times)).setTag(same(value));
                }
            }
        };
    }

    @Override
    protected Object getViewHolder() {
        return new BindWhenViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new BindWhenViewModel_ViewBinder().createBinder();
    }

    private class AlwaysEquals extends Object {

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}