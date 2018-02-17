package se.snylt.witchprocessortest.utils;


import android.view.View;

import java.util.HashMap;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestBinderHelper {

    private final Object viewHolder;

    private final TargetViewBinder targetViewBinder;

    private final ViewFinder viewFinder;

    private final HashMap<Integer, View> views = new HashMap<>();

    public TestBinderHelper(Object viewHolder, TargetViewBinder targetViewBinder) {
        this.viewHolder = viewHolder;
        this.targetViewBinder = targetViewBinder;
        this.viewFinder = mock(ViewFinder.class);
    }

    public View getView(int id) {
        return views.get(id);
    }

    public TestBinderHelper mockViewForId(int id, Class<? extends View> clazz) {
        View view = mock(clazz);
        when(viewFinder.findViewById(eq(id))).thenReturn(view);
        views.put(new Integer(id), view);
        return this;
    }

    public void bind(Object target) {
        for(ViewBinder binder: targetViewBinder.getViewBinders()) {
            binder.bind(viewHolder, viewFinder, target);
        }
    }
}
