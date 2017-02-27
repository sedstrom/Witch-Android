package se.snylt.zipperprocessortest.utils;

import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;
import se.snylt.zipperprocessortest.TestViewModel;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestViewBinderUtils {

    public interface VerifyPostBind<T> {

        void onPostBind(View mockedView, T viewModel, int viewId);
    }

    public static <T extends TestViewModel> void verifyViewBinder(List<ViewBinder> viewBinders, Object viewHolder, T viewModel, VerifyPostBind<T> verifyPostBind) {

        Class<? extends View> viewClass = viewModel.VIEW_CLASS;

        // Check correct view and value
        for (ViewBinder binder : viewBinders) {
            View view = mock(viewClass);
            ViewFinder viewFinder = viewFinderWithView(view);
            binder.bind(viewHolder, viewFinder, viewModel);
            verifyPostBind.onPostBind(view, viewModel, binder.viewId);
            break;
        }

    }

    private static ViewFinder viewFinderWithView(View view) {
        ViewFinder finder = mock(ViewFinder.class);
        when(finder.findViewById(anyInt())).thenReturn(view);
        return finder;
    }

}
