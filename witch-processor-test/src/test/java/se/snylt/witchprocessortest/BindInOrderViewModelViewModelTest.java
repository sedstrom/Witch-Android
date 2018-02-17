package se.snylt.witchprocessortest;


import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import android.widget.EditText;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static org.mockito.Mockito.mock;
// import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.viewFinderWithView;

/*
public class BindInOrderViewModelViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        // NA
    }

    @Test
    public void binds_ShouldBindInOrder() {
        BindInOrderViewModel model = new BindInOrderViewModel();
        EditText view = mock(EditText.class);
        InOrder inOrder = Mockito.inOrder(view);
        Object viewHolder = getViewHolder();
        ViewFinder viewFinder = viewFinderWithView(view);
        for(ViewBinder binder : getTargetViewBinder().getViewBinders()) {
            binder.bind(viewHolder, viewFinder, model);
        }
        inOrder.verify(view).setText("first");
        inOrder.verify(view).setText("second");
        inOrder.verify(view).setText("third");
    }

    @Override
    protected Object getViewHolder() {
        return new BindInOrderViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new BindInOrderViewModel_ViewBinder().createBinder();
    }
}
*/