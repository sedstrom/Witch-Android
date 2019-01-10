
package se.snylt.witchprocessortest;


import org.junit.Test;

import android.widget.TextView;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_WhenTest {

    private TestBinderHelper<Bind_When, Bind_When_ViewHolder> helper() {
        TestBinderHelper<Bind_When,  Bind_When_ViewHolder> helper = new TestBinderHelper<>(
                new  Bind_When_ViewHolder(),
                new  Bind_When_ViewBinder().createBinder());
        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.mockViewForId(R.id.testIdFour, TextView.class);
        return helper;
    }

    @Test
    public void bindWhen_Always() {
        Bind_When target = new Bind_When();
        TestBinderHelper<Bind_When, Bind_When_ViewHolder> helper = helper();
        target.always = "foo";
        helper.bind(target);
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdOne), times(2)).setText(eq("foo"));
    }

    @Test
    public void bindWhen_NotEquals() {
        Bind_When target = new Bind_When();
        TestBinderHelper<Bind_When, Bind_When_ViewHolder> helper = helper();

        target.notEquals = "foo";
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("foo"));

        target.notEquals = "foo";
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("foo"));

        target.notEquals = "bar";
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdTwo), times(1)).setText(eq("bar"));
    }

    @Test
    public void bindWhen_NotSame() {
        AlwaysEquals foo = new AlwaysEquals();
        AlwaysEquals equalsFoo = new AlwaysEquals();
        Bind_When target = new Bind_When();
        TestBinderHelper<Bind_When, Bind_When_ViewHolder> helper = helper();

        target.notSame = foo;
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(foo));

        target.notSame = foo;
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(foo));

        target.notSame = equalsFoo;
        helper.bind(target);
        verify((TextView)helper.getView(R.id.testIdThree), times(1)).setTag(same(equalsFoo));
    }

    @Test
    public void bindOnce() {
        Bind_When target = spy(new Bind_When());
        TestBinderHelper<Bind_When, Bind_When_ViewHolder> helper = helper();

        helper.bind(target);
        helper.bind(target);

        verify(target, times(1)).bind(any(TextView.class));
    }

    private class AlwaysEquals extends Object {

        @Override
        public boolean equals(Object obj) {
            return true;
        }
    }
}
