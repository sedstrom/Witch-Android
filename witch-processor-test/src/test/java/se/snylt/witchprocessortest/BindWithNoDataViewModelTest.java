package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BindWithNoDataViewModelTest {

    private TestBinderHelper<BindWithNoDataViewModel, BindWithNoDataViewModel_ViewHolder> helper() {
        TestBinderHelper<BindWithNoDataViewModel, BindWithNoDataViewModel_ViewHolder> helper
                = new TestBinderHelper<>(
                    new BindWithNoDataViewModel_ViewHolder(),
                    new BindWithNoDataViewModel_ViewBinder().createBinder());
        helper.mockViewForId(R.id.testIdOne, TextView.class);
        return helper;
    }

    @Test
    public void bindTwice_Should_BindTwice() {
        BindWithNoDataViewModel model = spy(new BindWithNoDataViewModel());
        TestBinderHelper<BindWithNoDataViewModel, BindWithNoDataViewModel_ViewHolder> helper = helper();
        TextView view = (TextView) helper.getView(R.id.testIdOne);
        helper.bind(model);
        helper.bind(model);
        verify(model, times(2)).bind(same(view));
    }
}