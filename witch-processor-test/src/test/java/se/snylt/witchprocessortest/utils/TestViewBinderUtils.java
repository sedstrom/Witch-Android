package se.snylt.witchprocessortest.utils;

import android.view.View;

import java.util.List;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;
import se.snylt.witchprocessortest.TestViewModel;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestViewBinderUtils {

    public interface VerifyPostBind<T> {

        void onPostBind(View mockedView, T viewModel, int viewId);
    }

    public interface ViewMocker {
        View mockView();
    }

    public static <T extends TestViewModel> void verifyViewBinder(List<ViewBinder> viewBinders, Object viewHolder, T viewModel,  VerifyPostBind<T> verifyPostBind) {
        verifyViewBinder(viewBinders, viewHolder, viewModel, new DefaultViewMocker(viewModel.VIEW_CLASS), verifyPostBind);
    }

    public static <T extends TestViewModel> void verifyViewBinder(List<ViewBinder> viewBinders, Object viewHolder, T viewModel, ViewMocker viewMocker, VerifyPostBind<T> verifyPostBind) {

        // Check correct view and value
        for (ViewBinder binder : viewBinders) {
            View view = viewMocker.mockView();
            ViewFinder viewFinder = mockViewFinder(view);
            binder.bind(viewHolder, viewFinder, viewModel);
            verifyPostBind.onPostBind(view, viewModel, binder.viewId);
        }
    }

    public static ViewFinder mockViewFinder(View view) {
        ViewFinder finder = mock(ViewFinder.class);
        return finder;
    }

    public static void addViewForId(ViewFinder viewFinder, View view, int id) {
        when(viewFinder.findViewById(id)).thenReturn(view);
    }

    public static void addViewForAnyId(ViewFinder viewFinder, View view) {
        when(viewFinder.findViewById(anyInt())).thenReturn(view);
    }

    private static class DefaultViewMocker implements ViewMocker {

        Class<? extends View> viewClass;

        DefaultViewMocker(Class<? extends View> viewClass) {
            this.viewClass = viewClass;
        }

        @Override
        public View mockView() {
            return mock(viewClass);
        }
    }
}
