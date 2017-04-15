package se.snylt.witch.viewbinder;

import java.util.List;

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
    }
}
