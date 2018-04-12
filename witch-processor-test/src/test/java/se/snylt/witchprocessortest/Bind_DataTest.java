package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_DataTest {

    @Test
    public void testAnnotations_BindData() {
        Bind_Data target = spy(new Bind_Data());
        TestBinderHelper<Bind_Data,Bind_Data_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_Data_ViewHolder(),
                        new Bind_Data_ViewBinder().createBinder());

        helper.bind(target);
        verify(target).foo(eq("foo"));

        helper.bind(target);
        verify(target, times(1)).foo(eq("foo"));
    }
}