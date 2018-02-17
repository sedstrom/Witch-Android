package se.snylt.witchprocessortest;

import org.junit.Test;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_SetupTest {

    @Test
    public void testAnnotations_Setup() {
        Bind_Setup target = spy(new Bind_Setup());
        TestBinderHelper<Bind_Setup,Bind_Setup_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new Bind_Setup_ViewHolder(),
                        new Bind_Setup_ViewBinder().createBinder());

        // Setup = @Bind + BindWhen(BindWhen.ONCE)
        helper.bind(target);
        helper.bind(target);

        verify(target, times(1)).setup();
    }
}