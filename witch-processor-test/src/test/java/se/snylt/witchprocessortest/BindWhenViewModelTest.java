
package se.snylt.witchprocessortest;


import org.junit.Test;

import android.view.View;
import android.widget.TextView;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BindWhenViewModelTest {

    private TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper() {
        TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper = new TestBinderHelper<>(
                new BindWhenViewModel_ViewHolder(),
                new BindWhenViewModel_ViewBinder().createBinder());
        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.mockViewForId(R.id.testIdFour, TextView.class);
        return helper;
    }

    @Test
    public void bindWhen_Always() {
        BindWhenViewModel model = new BindWhenViewModel();
        TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper = helper();
        model.always = "foo";
        helper.bind(model);
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdOne), times(2)).setText(eq("foo"));
    }

    @Test
    public void bindWhen_NotEquals() {
        BindWhenViewModel model = new BindWhenViewModel();
        TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper = helper();

        model.notEquals = "foo";
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("foo"));

        model.notEquals = "foo";
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("foo"));

        model.notEquals = "bar";
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("bar"));
    }

    @Test
    public void bindWhen_NotSame() {
        AlwaysEquals foo = new AlwaysEquals();
        AlwaysEquals equalsFoo = new AlwaysEquals();
        BindWhenViewModel model = new BindWhenViewModel();
        TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper = helper();

        model.notSame = foo;
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(foo));

        model.notSame = foo;
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(foo));

        model.notSame = equalsFoo;
        helper.bind(model);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(equalsFoo));
    }

    @Test
    public void bindOnce() {
        BindWhenViewModel model = spy(new BindWhenViewModel());
        TestBinderHelper<BindWhenViewModel, BindWhenViewModel_ViewHolder> helper = helper();

        helper.bind(model);
        helper.bind(model);

        verify(model, times(1)).bind(any(TextView.class));
    }

    private class AlwaysEquals extends Object {

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
