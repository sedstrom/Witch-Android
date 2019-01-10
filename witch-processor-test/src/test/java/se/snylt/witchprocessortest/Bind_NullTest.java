package se.snylt.witchprocessortest;

import android.widget.TextView;
import org.junit.Test;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class Bind_NullTest {

    @Test
    public void testAnnotations_BindNull() {
        Bind_Null target = spy(new Bind_Null());
        TestBinderHelper<Bind_Null, Bind_Null_ViewHolder> helper
                = new TestBinderHelper<>(
                new Bind_Null_ViewHolder(),
                new Bind_Null_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.mockViewForId(R.id.testIdFour, TextView.class);
        helper.mockViewForId(R.id.testIdFive, TextView.class);
        helper.bind(target);

        verify((TextView) helper.getView(R.id.testIdOne), times(1)).setText((CharSequence) isNull());
        verify((target)).two((String) isNull());
        verify((target), never()).four((String) isNull());
        verify((TextView) helper.getView(R.id.testIdFive), never()).setText((CharSequence) isNull());
    }
}