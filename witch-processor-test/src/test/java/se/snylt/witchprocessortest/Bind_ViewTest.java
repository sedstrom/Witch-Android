package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_ViewTest {

    @Test
    public void testAnnotations_BindView() {
        Bind_View target = spy(new Bind_View());
        TestBinderHelper<Bind_View,Bind_View_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_View_ViewHolder(),
                        new Bind_View_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);

        helper.bind(target);
        verify(target).foo(same((TextView)helper.getView(R.id.testIdOne)));

        helper.bind(target);
        verify(target, times(2)).foo(same((TextView)helper.getView(R.id.testIdOne)));
    }
}