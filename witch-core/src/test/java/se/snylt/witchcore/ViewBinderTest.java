package se.snylt.witchcore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import se.snylt.witchcore.bindaction.Binder;
import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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

    @Mock
    Binder<Object, String> binder;

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
        viewBinder = spy(new TestViewBinder(VIEW_ID, binder));
    }

    @Test
    public void bind_Should_InOrder_FindViewInViewFinder_SetViewInViewHolder_GetValueFromTarget_RunBindWithValueAndView(){
        InOrder inOrder = Mockito.inOrder(viewBinder, viewFinder, binder);

        viewBinder.setValue("123");

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        inOrder.verify(viewFinder).findViewById(eq(VIEW_ID));
        inOrder.verify(viewBinder).setView(same(viewHolder), same(bindView));
        inOrder.verify(viewBinder).getValue(same(target));
        inOrder.verify(binder).bind(same(bindView), eq("123"));
    }

    @Test
    public void bind_When_FirstValue_Should_RunBindWithValueAndView() {
        viewBinder.setValue("123");

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder).bind(same(bindView), eq("123"));
    }


    @Test
    public void bind_When_ValueNotDirty_Should_Never_Bind_SaveHistoryValue() {
        // When
        when(viewBinder.isDirty(any(Object.class))).thenReturn(false);
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder, never()).bind(any(Object.class), anyString());
        assertFalse(viewBinder.getHistoryValue().equals("123"));
    }

    @Test
    public void bind_When_ValueDirty_Should_Bind_SaveHistoryValue() {
        // When
        when(viewBinder.isDirty(any(Object.class))).thenReturn(true);
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder).bind(any(Object.class), eq("123"));
        assertEquals(viewBinder.getHistoryValue(), "123");
    }

    @Test
    public void bind_With_ViewSetInViewHolder_Should_Not_FindView_Or_SetViewInViewHolder(){
        viewBinder.setValue("123");

        // When
        Object view = mock(Object.class);
        viewHolder.setView(view);
        Mockito.reset(viewHolder);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewHolder, never()).setView(any(Object.class));
        verify(viewFinder, never()).findViewById(anyInt());
    }

    @Test
    public void bind_Twice_Should_AskForBinderTwice(){
        viewBinder.setValue("123");

        // When
        viewBinder.bind(viewHolder, viewFinder, target);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        assertEquals(2, viewBinder.getBinderCall.get());
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

        private Object value;

        AtomicInteger getBinderCall = new AtomicInteger();

        private Binder binder;

        TestViewBinder(int viewId, Binder binder) {
            super(viewId);
            this.binder = binder;
        }

        @Override
        public boolean isDirty(Object target) {
            return true;
        }

        @Override
        public Object getValue(Object target) {
            return this.value;
        }

        void setValue(Object value) {
            this.value = value;
        }

        @Override
        public void setView(Object viewHolder, Object view) {
            ((TestViewHolder)viewHolder).setView(view);
        }

        @Override
        public Object getView(Object viewHolder) {
            return ((TestViewHolder)viewHolder).getView();
        }
        @Override
        public Binder getBinder(Object value) {
            getBinderCall.incrementAndGet();
            return binder;
        }

        public Object getHistoryValue() {
            return historyValue;
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