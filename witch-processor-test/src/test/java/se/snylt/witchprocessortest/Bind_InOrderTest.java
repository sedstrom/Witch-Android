package se.snylt.witchprocessortest;

import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import android.view.View;
import android.widget.TextView;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.spy;

public class Bind_InOrderTest {

    @Test
    public void testBindInOrder() {
        Bind_InOrder target = spy(new Bind_InOrder());
        TestBinderHelper<Bind_InOrder, Bind_InOrder_ViewHolder> helper
                = new TestBinderHelper<>(
                new Bind_InOrder_ViewHolder(),
                new Bind_InOrder_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        InOrder inOrder = Mockito.inOrder(target);

        helper.bind(target);
        inOrder.verify(target).first();
        inOrder.verify(target).second();
        inOrder.verify(target).third();
    }
}