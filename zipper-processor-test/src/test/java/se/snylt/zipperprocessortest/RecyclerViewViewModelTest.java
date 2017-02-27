package se.snylt.zipperprocessortest;


import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import se.snylt.zipper.viewbinder.Binder;
import se.snylt.zipper.viewbinder.ViewBinder;
import se.snylt.zipperprocessortest.utils.TestViewBinderUtils;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static se.snylt.zipperprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public class RecyclerViewViewModelTest extends TestBase {

    @Override
    protected void testBind(List<ViewBinder> binder, Object viewHolder) {
        verifyViewBinder(
                binder,
                viewHolder,
                new RecyclerViewViewModel(),
                getViewMocker(),
                new TestViewBinderUtils.VerifyPostBind<RecyclerViewViewModel>() {
                    @Override
                    public void onPostBind(View mockedView, RecyclerViewViewModel viewModel, int viewId) {
                        verify((RecyclerViewAdapter)((RecyclerView)mockedView).getAdapter()).setItems(same(viewModel.items));
                    }
                }
        );
    }

    @Override
    protected Object getViewHolder() {
        return new RecyclerViewViewModel_ViewHolder();
    }

    @Override
    protected Binder getBinder() {
        return new RecyclerViewViewModel_ViewBinder().createBinder();
    }

    public TestViewBinderUtils.ViewMocker getViewMocker() {
        return new TestViewBinderUtils.ViewMocker() {
            @Override
            public View mockView() {
                RecyclerViewAdapter adapter = mock(RecyclerViewAdapter.class);
                RecyclerView view = mock(RecyclerView.class);
                when(view.getAdapter()).thenReturn(adapter);
                return view;
            }
        };
    }
}