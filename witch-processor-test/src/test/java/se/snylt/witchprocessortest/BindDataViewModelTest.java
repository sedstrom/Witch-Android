package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class BindDataViewModelTest {

    @Test
    public void testAnnotations_BindData_Data_Bind() {
        BindDataViewModel model = new BindDataViewModel();
        TestBinderHelper<BindDataViewModel,BindDataViewModel_ViewHolder> helper
                    = new TestBinderHelper<>(
                        new BindDataViewModel_ViewHolder(),
                        new BindDataViewModel_ViewBinder().createBinder());

        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.bind(model);

        verify((TextView)helper.getView(R.id.testIdOne)).setText(eq(model.foo));
        verify((TextView)helper.getView(R.id.testIdTwo)).setText(eq(model.bar()));
        verify((TextView)helper.getView(R.id.testIdThree)).setText(eq(model.doe.get()));
    }
}