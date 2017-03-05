package se.snylt.witchprocessortest;


import java.util.List;

import se.snylt.witch.viewbinder.Binder;
import se.snylt.witch.viewbinder.ViewBinder;

import static junit.framework.Assert.assertTrue;

public class AlwaysBindViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        assertTrue(binder.get(0).isAlwaysBind());
    }

    @Override
    protected Object getViewHolder() {
        return new AlwaysBindViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new AlwaysBindViewModel_ViewBinder().createBinder();
    }
}