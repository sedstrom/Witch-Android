package se.snylt.witchcore;

import java.util.List;

import se.snylt.witchcore.viewbinder.ViewBinder;

public class TestViewModel_ViewBinder implements TargetViewBinderFactory {

    public final TestTargetViewBinder binder = new TestTargetViewBinder(null);

    @Override
    public TargetViewBinder createBinder() {
        return binder;
    }

    public static class TestTargetViewBinder extends TargetViewBinder {

        public TestTargetViewBinder(List<ViewBinder> viewBinders) {
            super(viewBinders);
        }

        @Override
        public String describeTarget(Object o) {
            return null;
        }
    }
}
