package se.snylt.witchprocessortest;


import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;

import static junit.framework.Assert.assertTrue;

public class AlwaysBindViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        assertTrue(binder.get(0).isDirty(null));
    }

    @Override
    protected Object getViewHolder() {
        return new AlwaysBindViewModel_ViewHolder();
    }

    @Override
    protected TargetViewBinder getTargetViewBinder() {
        return new AlwaysBindViewModel_ViewBinder().createBinder();
    }
}