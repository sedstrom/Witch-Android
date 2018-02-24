package se.snylt.witchcore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import se.snylt.witchcore.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WitchCoreTest {

    private WitchCore core;

    private final String targetDescription = "foo";

    @Mock
    private
    ViewHolderFactory viewHolderFactory;

    @Mock
    private
    Object viewHolderFromFactory;

    @Mock
    private
    BinderFactory binderFactory;

    @Mock
    private
    TargetViewBinder<Object, Object> targetViewBinderFromFactory;

    @Mock
    private
    Object target;

    @Mock
    private
    ViewFinder viewFinder;

    @Mock
    private
    Logger logger;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        when(targetViewBinderFromFactory.describeTarget(any())).thenReturn(targetDescription);
        when(viewHolderFactory.createViewHolder(same(target))).thenReturn(viewHolderFromFactory);
        when(binderFactory.createBinder(same(target))).thenReturn(targetViewBinderFromFactory);
        core = new WitchCore(viewHolderFactory, binderFactory, logger);
    }

    @Test
    public void doBind_Should_Call_Binder_bind() {
        // When
        core.doBind(target, viewFinder);

        // Then
        verify(targetViewBinderFromFactory).bind(same(viewHolderFromFactory), same(viewFinder), same(target));
    }

    @Test
    public void doBind_When_NoViewHolderPut_Should_CreateViewHolder_PutViewHolderWithTargetClass_BindWithViewHolder() {
        // When
        core.doBind(target, viewFinder);

        // Then
        verify(viewFinder).putViewHolder(same(target.getClass()), same(viewHolderFromFactory));
        verify(targetViewBinderFromFactory).bind(same(viewHolderFromFactory), same(viewFinder), same(target));
    }

    @Test
    public void doBind_When_ViewHolderPut_Should_Not_PutAnyViewHolder_BindWithViewHolder() {
        // When
        Object viewHolder = mock(Object.class);
        viewHolderForKey(viewFinder, target.getClass(), viewHolder);
        core.doBind(target, viewFinder);

        // Then
        verify(viewFinder, never()).putViewHolder(any(Class.class), any(Object.class));
        verify(targetViewBinderFromFactory).bind(same(viewHolder), same(viewFinder), same(target));
    }

    @Test
    public void doBind_When_NoViewBinderPut_Should_CreateViewBinder_PutViewBinderWithTargetClass_BindWithViewBinder() {
        // When
        core.doBind(target, viewFinder);

        // Then
        verify(viewFinder).putBinder(same(target.getClass()), same(targetViewBinderFromFactory));
        verify(targetViewBinderFromFactory).bind(same(viewHolderFromFactory), same(viewFinder), same(target));
    }

    @Test
    public void doBind_When_ViewBinderPut_Should_NotPutAnyViewBinder_BindWithViewBinder() {
        // When
        TargetViewBinder<Object, Object> targetViewBinder = mock(TargetViewBinder.class);
        viewBinderForKey(viewFinder, target.getClass(), targetViewBinder);
        core.doBind(target, viewFinder);

        // Then
        verify(viewFinder, never()).putBinder(any(Class.class), any(TargetViewBinder.class));
        verify(targetViewBinder).bind(same(viewHolderFromFactory), same(viewFinder), same(target));
    }

    @Test
    public void doBind_When_LoggingEnabled_Should_Log(){
        // When
        core.setLoggingEnabled(true);
        core.doBind(target, viewFinder);

        // Then
        verify(logger).v(eq(targetDescription));
    }

    @Test
    public void doBind_When_LoggingDisabled_Should_Never_Log(){
        // When
        core.setLoggingEnabled(false);
        core.doBind(target, viewFinder);

        // Then
        verify(logger, never()).v(anyString());
    }

    private void viewBinderForKey(ViewFinder viewFinder, Class<?> key, TargetViewBinder viewTargetViewBinder) {
        when(viewFinder.getBinder(same(key))).thenReturn(viewTargetViewBinder);
    }

    private void viewHolderForKey(ViewFinder viewFinder, Class<?> key, Object viewHolder) {
        when(viewFinder.getViewHolder(same(key))).thenReturn(viewHolder);
    }

}