package se.snylt.witchcore;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import se.snylt.witchcore.bindaction.Binder;
import se.snylt.witchcore.bindaction.OnBind;
import se.snylt.witchcore.bindaction.OnBindListener;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class BinderTest {

    private Binder<Object, Object> binder;

    @Mock
    Object target;

    @Mock
    Object value;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_With_NoOnBinds_ShouldDoNothingOnBind(){
        // When
        binder = Binder.create();
        binder.bind(target, value);

        // Then
        Mockito.verifyZeroInteractions(target, value);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void create_With_OnBind_Should_RunOnBind(){
        // When
        OnBind<Object, Object> onBind = mock(OnBind.class);
        binder = Binder.create(onBind);
        binder.bind(target, value);

        // Then
        verify(onBind).bind(same(target), same(value), any(OnBindListener.class));
    }

    @Test
    public void next_Should_RunOnBindsInOrder(){
        // When
        OnBind<Object, Object> first = spy(instantOnBind());
        OnBind<Object, Object> second = spy(instantOnBind());
        InOrder inOrder = Mockito.inOrder(first, second);

        binder = Binder.create(first).next(second);
        binder.bind(target, value);

        // Then
        inOrder.verify(first).bind(same(target), same(value), any(OnBindListener.class));
        inOrder.verify(second).bind(same(target), same(value), any(OnBindListener.class));
    }

    @Test
    public void next_Should_CreateNewBinderFromEachCall(){
        OnBind<Object, Object> first = spy(instantOnBind());
        OnBind<Object, Object> second = spy(instantOnBind());

        // First binder should only have create OnBind
        Binder<Object, Object> firstBinder = Binder.create(first);
        firstBinder.bind(target, value);
        verify(first, times(1)).bind(same(target), same(value), any(OnBindListener.class));
        verify(second, times(0)).bind(same(target), same(value), any(OnBindListener.class));

        // Chain with second OnBind and both should be in chain.
        Binder<Object, Object> firstAndSecondBinder = firstBinder.next(second);
        firstAndSecondBinder.bind(target, value);
        verify(first, times(2)).bind(same(target), same(value), any(OnBindListener.class));
        verify(second, times(1)).bind(same(target), same(value), any(OnBindListener.class));
    }

    @Test
    public void next_Should_NotAffectSourceBinder(){
        OnBind<Object, Object> first = spy(instantOnBind());
        OnBind<Object, Object> second = spy(instantOnBind());

        Binder<Object, Object> b = Binder.create(first);
        b.next(second); // Should not affect b

        b.bind(target, value);
        verify(first, times(1)).bind(same(target), same(value), any(OnBindListener.class));
        verify(second, times(0)).bind(same(target), same(value), any(OnBindListener.class));
    }

    @Test
    public void bind_Should_SupportMultipleRuns(){
        OnBind<Object, Object> onBind = spy(instantOnBind());
        Binder<Object, Object> b = Binder.create(onBind);

        // When
        b.bind(target, value);
        b.bind(target, value);

        // Then
        verify(onBind, times(2)).bind(same(target), same(value), any(OnBindListener.class));
    }

    @Test
    public void bind_With_ParallelOnBinds_Should_RunOnBindsInParallel(){
        TestOnBind<Object, Object> first = new TestOnBind<>();
        TestOnBind<Object, Object> second = new TestOnBind<>();
        TestOnBind<Object, Object> third = mock(TestOnBind.class);

        // Create binder with two actions in first step, and one in second step.
        Binder.create(first, second).next(third).bind(target, value);
        verify(third, never()).bind(same(target), same(value), any(OnBindListener.class));

        // First completes => should not trigger third bind
        first.onBindListener.onBindDone();
        verify(third, never()).bind(same(target), same(value), any(OnBindListener.class));

        // Second completes => should trigger third bind
        second.onBindListener.onBindDone();
        verify(third, times(1)).bind(same(target), same(value), any(OnBindListener.class));

    }

    private OnBind<Object, Object> instantOnBind(){
        return new OnBind<Object, Object>() {
            @Override
            public void bind(Object o, Object o2, OnBindListener onBindListener) {
                onBindListener.onBindDone();
            }
        };
    }

    private class TestOnBind<Target, Value> implements OnBind<Target, Value> {

        OnBindListener onBindListener;

        @Override
        public void bind(Target target, Value value, OnBindListener onBindListener) {
            this.onBindListener = onBindListener;
        }
    }
}