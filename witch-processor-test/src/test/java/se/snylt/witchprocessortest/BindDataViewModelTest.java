package se.snylt.witchprocessortest;


import android.view.View;
import android.widget.TextView;

import org.junit.Test;
import java.util.List;
import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestBinderHelper;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class BindDataViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        // NA
    }

    @Test
    public void testBindData() {
        BindDataViewModel model = new BindDataViewModel();
        TestBinderHelper helper = new TestBinderHelper(getViewHolder(), getTargetViewBinder());
        helper.mockViewForId(R.id.testIdOne, TextView.class);
        helper.mockViewForId(R.id.testIdTwo, TextView.class);
        helper.mockViewForId(R.id.testIdThree, TextView.class);
        helper.bind(model);

        verify((TextView)helper.getView(R.id.testIdOne)).setText(eq(model.foo));
        verify((TextView)helper.getView(R.id.testIdTwo)).setText(eq(model.bar()));
        verify((TextView)helper.getView(R.id.testIdThree)).setText(eq(model.doe.get()));
    }

    @Override
    protected Object getViewHolder() {
        return new BindDataViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new BindDataViewModel_ViewBinder().createBinder();
    }
}