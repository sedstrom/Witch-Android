package se.snylt.witch.viewbinder;

import java.util.List;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

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

        if(Witch.isLoggingEnabled() && printer != null) {
            printer.printTarget(target);
        }
    }

    // Test
    public final List<ViewBinder<Target, ?, ?, ViewHolder>> getViewBinders() {
        return viewBinders;
    }
}