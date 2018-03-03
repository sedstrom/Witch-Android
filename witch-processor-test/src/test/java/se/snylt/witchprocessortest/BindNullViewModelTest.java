package se.snylt.witchprocessortest;

import android.widget.TextView;
import org.junit.Test;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BindNullViewModelTest {

    @Test
    public void testAnnotations_BindNull() {
        BindNullViewModel model = new BindNullViewModel();
        TestBinderHelper<BindNullViewModel, BindNullViewModel_ViewHolder> helper
                = new TestBinderHelper<>(
                new BindNullViewModel_ViewHolder(),
                new BindNullViewModel_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.mockViewForId(R.id.testIdFour, TextView.class);
        helper.mockViewForId(R.id.testIdFive, TextView.class);
        helper.bind(model);

        verify((TextView) helper.getView(R.id.testIdOne), times(1)).setText((CharSequence) isNull());
        verify((TextView) helper.getView(R.id.testIdTwo), times(1)).setText((CharSequence) isNull());
        verify((TextView) helper.getView(R.id.testIdThree), times(1)).setText((CharSequence) isNull());
        verify((TextView) helper.getView(R.id.testIdFour), never()).setText(isNull(String.class));
        verify((TextView) helper.getView(R.id.testIdFive), never()).setText(isNull(String.class));
    }
}