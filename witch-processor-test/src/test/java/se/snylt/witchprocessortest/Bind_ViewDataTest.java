package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class Bind_ViewDataTest {

    @Test
    public void testAnnotations_Bind_ViewData() {
        Bind_ViewData target = spy(new Bind_ViewData());
        TestBinderHelper<Bind_ViewData,Bind_ViewData_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_ViewData_ViewHolder(),
                        new Bind_ViewData_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.bind(target);

        verify(target).foo(same((TextView)helper.getView(R.id.testIdOne)), eq("foo"));
        verify(target).bar(same((TextView)helper.getView(R.id.testIdTwo)), eq(target.bar));
    }
}