package se.snylt.witchprocessortest;

import android.widget.TextView;

import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InOrder;
import org.mockito.Mockito;

import se.snylt.witchprocessortest.utils.TestBinderHelper;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.spy;

public class BindWithHistoryViewModelTest {

    private TestBinderHelper<BindWithHistoryViewModel, BindWithHistoryViewModel_ViewHolder> helper() {
        TestBinderHelper<BindWithHistoryViewModel, BindWithHistoryViewModel_ViewHolder> helper
                = new TestBinderHelper<>(
                    new BindWithHistoryViewModel_ViewHolder(),
                    new BindWithHistoryViewModel_ViewBinder().createBinder());
        helper.mockViewForId(R.id.testIdOne, TextView.class);
        return helper;
    }

    @Test
    public void bind_Should_SupportHistory() {
        BindWithHistoryViewModel model = spy(new BindWithHistoryViewModel());
        InOrder inOrder = Mockito.inOrder(model);
        TestBinderHelper<BindWithHistoryViewModel, BindWithHistoryViewModel_ViewHolder> helper = helper();
        TextView view = (TextView) helper.getView(R.id.testIdOne);
        model.data = "First";
        helper.bind(model);
        model.data = "Second";
        helper.bind(model);
        inOrder.verify(model).data(same(view), eq("First"), ArgumentMatchers.<String>isNull());
        inOrder.verify(model).data(same(view), eq("Second"), eq("First"));
    }
}