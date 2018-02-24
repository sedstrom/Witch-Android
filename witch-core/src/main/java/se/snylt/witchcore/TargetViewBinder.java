package se.snylt.witchcore;

import java.util.List;

import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;

public class TargetViewBinder<Target, ViewHolder> {

    private final List<ViewBinder<Target, ?, ?, ViewHolder>> viewBinders;

    private final TargetPrinter<Target> printer;

    public TargetViewBinder(List<ViewBinder<Target, ?, ?, ViewHolder>> viewBinders, TargetPrinter<Target> printer) {
        this.viewBinders = viewBinders;
        this.printer = printer;
    }

    public TargetViewBinder(List<ViewBinder<Target, ?, ?,ViewHolder>> viewBinders) {
        this(viewBinders, null);
    }

    public void bind(ViewHolder viewHolder, ViewFinder viewFinder, Target target) {
        for (ViewBinder<Target, ?, ?, ViewHolder> viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }

        if(WitchCore.isLoggingEnabled() && printer != null) {
            printer.printTarget(target);
        }
    }

    // Test
    public final List<ViewBinder<Target, ?, ?, ViewHolder>> getViewBinders() {
        return viewBinders;
    }
}