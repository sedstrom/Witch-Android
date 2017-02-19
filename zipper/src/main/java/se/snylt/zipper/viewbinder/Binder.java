package se.snylt.zipper.viewbinder;

import java.util.List;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public class Binder {

    private final List<ViewBinder> viewBinders;

    public Binder(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    public void bind(Object viewHolder, ViewFinder viewFinder, Object target, Object ...mods) {
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target, mods);
        }
    }
}