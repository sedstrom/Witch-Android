package se.snylt.witchcore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;

import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ViewBinderTest {

    private final static int VIEW_ID = 666;

    @Mock
    Object rootView;

    @Mock
    Object bindView;

    private TestViewBinder viewBinder;

    private TestViewHolder viewHolder;

    private TestViewFinder viewFinder;

    private Object target;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        target = new Object();

        viewFinder = spy(new TestViewFinder(rootView));
        viewFinder.childView(VIEW_ID, bindView);
        viewHolder = spy(new TestViewHolder());
        viewBinder = spy(new TestViewBinder(VIEW_ID));
    }

    @Test
    public void bind_Should_InOrder_FindViewInViewFinder_SetViewInViewHolder_GetDataFromTarget_RunBindWithTargetDataHistoryDataAndView(){
        InOrder inOrder = Mockito.inOrder(viewBinder, viewFinder);

        Object data = new Object();
        viewBinder.setData(data);

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        inOrder.verify(viewFinder).findViewById(eq(VIEW_ID));
        inOrder.verify(viewBinder).setView(same(viewHolder), same(bindView));
        inOrder.verify(viewBinder).getData(same(target));
        inOrder.verify(viewBinder).bind(same(target), same(bindView), same(data), isNull());
    }

    @Test
    public void bind_When_FirstData_Should_RunBindWithDataAndView() {
        Object data = new Object();
        viewBinder.setData(data);

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewBinder).bind(same(target), same(bindView), same(data), isNull());
    }


    @Test
    public void bind_When_DataNotDirty_Should_Never_Bind_SaveHistoryData() {
        // When
        when(viewBinder.isDirty(any(Object.class))).thenReturn(false);
        viewBinder.setData("123");
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewBinder, never()).bind(any(Object.class), any(Object.class), anyString(), anyString());
        assertFalse(viewBinder.getHistoryData().equals("123"));
    }

    @Test
    public void bind_When_DataDirty_Should_Bind_SaveHistoryData() {
        // When
        when(viewBinder.isDirty(any(Object.class))).thenReturn(true);
        Object data = new Object();
        viewBinder.setData(data);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewBinder).bind(same(target), same(bindView), same(data), isNull());
        assertSame(viewBinder.getHistoryData(), data);
    }

    @Test
    public void bindTwice_Should_BindWithHistoryNull_Then_HistoryData() {
        InOrder inOrder = Mockito.inOrder(viewBinder);
        Object first = new Object();
        Object second = new Object();

        // When
        when(viewBinder.isDirty(any(Object.class))).thenReturn(true);
        viewBinder.setData(first);
        viewBinder.bind(viewHolder, viewFinder, target);
        viewBinder.setData(second);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        inOrder.verify(viewBinder).bind(same(target), same(bindView), same(first), isNull());
        inOrder.verify(viewBinder).bind(same(target), same(bindView), same(second), same(first));
    }

    @Test
    public void bind_With_ViewSetInViewHolder_Should_Not_FindView_Or_SetViewInViewHolder(){
        viewBinder.setData("123");

        // When
        Object view = mock(Object.class);
        viewHolder.setView(view);
        Mockito.reset(viewHolder);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewHolder, never()).setView(any(Object.class));
        verify(viewFinder, never()).findViewById(anyInt());
    }

    private class TestViewHolder {

        private Object view;

        public Object getView() {
            return view;
        }

        public void setView(Object view) {
            this.view = view;
        }
    }


    private class TestViewBinder extends ViewBinder {

        private Object data;

        TestViewBinder(int viewId) {
            super(viewId);
        }

        @Override
        public boolean isDirty(Object target) {
            return true;
        }

        @Override
        public void bind(Object o, Object o2, Object data, Object historyData) {

        }

        @Override
        public Object getData(Object target) {
            return this.data;
        }

        void setData(Object data) {
            this.data = data;
        }

        @Override
        public void setView(Object viewHolder, Object view) {
            ((TestViewHolder)viewHolder).setView(view);
        }

        @Override
        public Object getView(Object viewHolder) {
            return ((TestViewHolder)viewHolder).getView();
        }

        public Object getHistoryData() {
            return historyData;
        }
    }

    private class TestViewFinder implements ViewFinder {

        private final Object rootView;

        private HashMap<Integer, Object> views = new HashMap();

        private TestViewFinder(Object rootView) {
            this.rootView = rootView;
        }

        @Override
        public Object getRoot() {
            return rootView;
        }

        @Override
        public int getTag() {
            return 0;
        }

        @Override
        public Object findViewById(int id) {
            return views.get(id);
        }

        @Override
        public Object getViewHolder(Object key) {
            return null;
        }

        @Override
        public void putViewHolder(Object key, Object viewHolder) {

        }

        @Override
        public TargetViewBinder getBinder(Object key) {
            return null;
        }

        @Override
        public void putBinder(Object key, TargetViewBinder targetViewBinder) {

        }

        void childView(int viewId, Object view) {
            views.put(viewId, view);
        }
    }
}