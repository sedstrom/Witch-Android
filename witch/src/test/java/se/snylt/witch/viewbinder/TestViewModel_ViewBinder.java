package se.snylt.witch.viewbinder;

import java.util.List;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;

public class TestViewModel_ViewBinder implements TargetViewBinderFactory {

    public final TestTargetViewBinder binder = new TestTargetViewBinder(null, null);

    @Override
    public TargetViewBinder createBinder() {
        return binder;
    }

    public static class TestTargetViewBinder extends TargetViewBinder {

        public TestTargetViewBinder(List<ViewBinder> viewBinders, TargetPrinter printer) {
            super(viewBinders, printer);
        }
    }
}
