package se.snylt.witchcore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import se.snylt.witchcore.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WitchCoreTest {

    private WitchCore core;

    @Mock
    ViewHolderFactory viewHolderFactory;

    @Mock
    Object viewHolderFromFactory;

    @Mock
    BinderFactory binderFactory;

    @Mock
    TargetViewBinder targetViewBinderFromFactory;

    @Mock
    Object target;

    @Mock
    ViewFinder viewFinder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(viewHolderFactory.createViewHolder(same(target))).thenReturn(viewHolderFromFactory);
        when(binderFactory.createBinder(same(target))).thenReturn(targetViewBinderFromFactory);
        core = new WitchCore(viewHolderFactory, binderFactory);
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
        TargetViewBinder targetViewBinder = mock(TargetViewBinder.class);
        viewBinderForKey(viewFinder, target.getClass(), targetViewBinder);
        core.doBind(target, viewFinder);

        // Then
        verify(viewFinder, never()).putBinder(any(Class.class), any(TargetViewBinder.class));
        verify(targetViewBinder).bind(same(viewHolderFromFactory), same(viewFinder), same(target));
    }

    private void viewBinderForKey(ViewFinder viewFinder, Class<?> key, TargetViewBinder viewTargetViewBinder) {
        when(viewFinder.getBinder(same(key))).thenReturn(viewTargetViewBinder);
    }

    private void viewHolderForKey(ViewFinder viewFinder, Class<?> key, Object viewHolder) {
        when(viewFinder.getViewHolder(same(key))).thenReturn(viewHolder);
    }

}