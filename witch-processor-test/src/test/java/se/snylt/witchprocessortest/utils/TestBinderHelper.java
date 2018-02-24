package se.snylt.witchprocessortest.utils;

import android.view.View;
import java.util.HashMap;
import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestBinderHelper<Target, ViewHolder> {

    private final ViewHolder viewHolder;

    private final TargetViewBinder<Target, ViewHolder> targetViewBinder;

    private final ViewFinder viewFinder;

    private final HashMap<Integer, View> views = new HashMap<>();

    public TestBinderHelper(ViewHolder viewHolder, TargetViewBinder<Target, ViewHolder> targetViewBinder) {
        this.viewHolder = viewHolder;
        this.targetViewBinder = targetViewBinder;
        this.viewFinder = mock(ViewFinder.class);
    }

    public View getView(int id) {
        return views.get(id);
    }

    public View mockViewForId(int id, Class<? extends View> clazz) {
        View view = mock(clazz);
        when(viewFinder.findViewById(eq(id))).thenReturn(view);
        views.put(new Integer(id), view);
        return view;
    }

    public void bind(Target target) {
        for(ViewBinder<Target, ?, ?, ViewHolder> binder: targetViewBinder.getViewBinders()) {
            binder.bind(viewHolder, viewFinder, target);
        }
    }
}
