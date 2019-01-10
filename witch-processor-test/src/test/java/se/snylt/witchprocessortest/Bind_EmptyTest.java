package se.snylt.witchprocessortest;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class Bind_EmptyTest {

    @Test
    public void testAnnotations_Bind() {
        Bind_Empty target = spy(new Bind_Empty());
        TestBinderHelper<Bind_Empty,Bind_Empty_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_Empty_ViewHolder(),
                        new Bind_Empty_ViewBinder().createBinder());

        helper.bind(target);

        verify(target).foo();
    }
}