package se.snylt.witchprocessortest;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class Bind_DataDataTest {

    @Test
    public void testAnnotations_BindDataData() {
        Bind_DataData target = spy(new Bind_DataData());
        TestBinderHelper<Bind_DataData,Bind_DataData_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_DataData_ViewHolder(),
                        new Bind_DataData_ViewBinder().createBinder());

        helper.bind(target);
        verify(target).foo(eq("foo"), (String) isNull());

        helper.bind(target);
        verify(target).foo(eq("foo"), eq("foo"));
    }
}