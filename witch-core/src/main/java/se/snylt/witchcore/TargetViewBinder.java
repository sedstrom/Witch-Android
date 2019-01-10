package se.snylt.witchcore;

import java.util.List;

import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;

public abstract class TargetViewBinder<Target, ViewHolder> {

    private final List<ViewBinder<Target, ?, ?, ViewHolder>> viewBinders;

    public TargetViewBinder(List<ViewBinder<Target, ?, ?, ViewHolder>> viewBinders) {
        this.viewBinders = viewBinders;
    }

    public void bind(ViewHolder viewHolder, ViewFinder viewFinder, Target target) {
        for (ViewBinder<Target, ?, ?, ViewHolder> viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }
    }

    public abstract String describeTarget(Target target);

    // Test
    public final List<ViewBinder<Target, ?, ?, ViewHolder>> getViewBinders() {
        return viewBinders;
    }
}