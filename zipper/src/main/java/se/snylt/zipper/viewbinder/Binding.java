package se.snylt.zipper.viewbinder;

import java.util.List;

public class Binding {

    private final List<ViewBinder> viewBinders;

    private final OnUnbindListener onUnbindListener;

    public Binding(List<ViewBinder> viewBinders, OnUnbindListener onUnbindListener) {
        this.viewBinders = viewBinders;
        this.onUnbindListener = onUnbindListener;
    }

    public List<ViewBinder> getViewBinders() {
        return viewBinders;
    }

    public void unBind() {
        onUnbindListener.onUnbind();
    }
}
