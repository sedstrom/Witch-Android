package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_ViewDataDataTest {

    @Test
    public void testAnnotations_Bind_ViewDataData() {
        Bind_ViewDataData target = spy(new Bind_ViewDataData());
        TestBinderHelper<Bind_ViewDataData,Bind_ViewDataData_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_ViewDataData_ViewHolder(),
                        new Bind_ViewDataData_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);

        helper.bind(target);
        verify(target, times(1)).foo(same((TextView) helper.getView(R.id.testIdOne)), eq("foo"), (String) isNull());

        helper.bind(target);
        verify(target, times(1)).foo(same((TextView) helper.getView(R.id.testIdOne)), eq("foo"), eq("foo"));

    }
}