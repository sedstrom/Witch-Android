package se.snylt.witch.viewbinder;

import java.util.List;

public class TestViewModel_ViewBinder implements BindingCreator {

    public final TestBinder binder = new TestBinder(null);

    @Override
    public Binder createBinder() {
        return binder;
    }

    public static class TestBinder extends Binder {

        public TestBinder(List<ViewBinder> viewBinders) {
            super(viewBinders);
        }
    }
}
