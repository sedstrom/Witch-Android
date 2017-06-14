package se.snylt.witch.viewbinder;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.viewbinder.DefaultViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class DefaultViewBinderTest {

    private final static int VIEW_ID = 666;

    @Mock
    View rootView;

    @Mock
    View bindView;

    @Mock
    Binder<View, String> binder;

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
    public void bind_Should_InOrder_GetValueFromTarget_FindViewInViewFinder_SetViewInViewHolder_RunBindWithValueAndView(){
        InOrder inOrder = Mockito.inOrder(viewBinder, viewFinder, binder);

        viewBinder.setValue("123");

        // When
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        inOrder.verify(viewBinder).getValue(same(target));
        inOrder.verify(viewFinder).findViewById(eq(VIEW_ID));
        inOrder.verify(viewBinder).setView(same(viewHolder), same(bindView));
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
    public void bind_When_IsNewValue_Should_Never_RunBindWithValueAndView() {
        // When
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);
        viewBinder.setValue("456"); // New value
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder).bind(same(bindView), eq("123"));
        verify(binder).bind(same(bindView), eq("456"));
    }

    @Test
    public void bind_When_IsNotNewValue_Should_Never_RunBindWithValueAndView() {
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);
        Mockito.reset((Binder)binder);

        // When
        viewBinder.setValue("123"); // Same value again
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder, never()).bind(any(View.class), anyString());
    }

    /*
    @Test
    public void bind_When_IsNotNewValue_IsAlwaysBind_Should_RunBindWithValueAndView() {
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);
        Mockito.reset((Binder)binder);

        // When
        isAlwaysBind(true);
        viewBinder.setValue("123");
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(binder).bind(same(bindView), eq("123"));
    }

    private void isAlwaysBind(boolean alwaysBind) {
        viewBinder.setAlwaysBind(alwaysBind);
    }
    */

    @Test
    public void bind_With_ViewSetInViewHolder_Should_Not_FindView_Or_SetViewInViewHolder(){
        viewBinder.setValue("123");

        // When
        View view = mock(View.class);
        viewHolder.setView(view);
        Mockito.reset(viewHolder);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewHolder, never()).setView(any(View.class));
        verify(viewFinder, never()).findViewById(anyInt());
    }

    @Test
    public void bind_With_SameValue_Should_NotKeepAsHistoryReference(){
        Binder<View, Object> binder = Binder.create();
        viewBinder = spy(new TestViewBinder(VIEW_ID, binder));
        Object value = new ArrayList<>();
        Object otherEqualValue = new ArrayList<>();

        // When
        viewBinder.setValue(value);
        viewBinder.bind(viewHolder, viewFinder, target);
        viewBinder.setValue(otherEqualValue);
        viewBinder.bind(viewHolder, viewFinder, target);

        // Then
        assertEquals(value, otherEqualValue);
        assertNotSame(otherEqualValue, viewBinder.getHistoryValue());
        assertSame(value, viewBinder.getHistoryValue());
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


    private class TestViewBinder extends DefaultViewBinder {

        private Object value;

        TestViewBinder(int viewId, Binder binder) {
            super(viewId, binder);
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
            return binder;
        }

        public Object getHistoryValue() {
            return historyValue;
        }
    }

    private class TestViewFinder implements ViewFinder {

        private final View rootView;

        private HashMap<Integer, View> views = new HashMap();

        private TestViewFinder(View rootView) {
            this.rootView = rootView;
        }

        @Override
        public View getRoot() {
            return rootView;
        }

        @Override
        public int getTag() {
            return 0;
        }

        @Override
        public View findViewById(int id) {
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

        void childView(int viewId, View view) {
            views.put(viewId, view);
        }
    }
}