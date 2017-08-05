package se.snylt.witch.viewbinder;

import java.util.List;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

public class TargetViewBinder {

    private final List<ViewBinder> viewBinders;

    private final TargetPrinter printer;

    public TargetViewBinder(List<ViewBinder> viewBinders, TargetPrinter printer) {
        this.viewBinders = viewBinders;
        this.printer = printer;
    }

    public TargetViewBinder(List<ViewBinder> viewBinders) {
        this(viewBinders, null);
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }

        if(Witch.isLoggingEnabled() && printer != null) {
            printer.printTarget(target);
        }
    }

    // Test
    public final List<ViewBinder> getViewBinders() {
        return viewBinders;
    }
}