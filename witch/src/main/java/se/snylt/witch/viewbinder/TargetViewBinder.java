package se.snylt.witch.viewbinder;

import java.util.List;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public class TargetViewBinder {

    private final List<ViewBinder> viewBinders;

    public TargetViewBinder(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }
    }

    // Test
    public final List<ViewBinder> getViewBinders() {
        return viewBinders;
    }
}