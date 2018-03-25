package se.snylt.witch.android;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.view.View;

import se.snylt.witchcore.WitchCore;
import se.snylt.witchcore.viewfinder.ViewFinder;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class WitchTest {

    @Mock
    WitchCore core;

    @Mock
    Object target;

    @Mock
    Activity activity;

    @Mock
    View activityContentView;

    @Mock
    View view;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        when(activity.findViewById(android.R.id.content)).thenReturn(activityContentView);
        WitchTestUtils.testInit(core);
    }

    @Test
    public void bind_WhitActivity_Should_Call_WitchCore_doBind_With_Target_And_ViewBinderWithActivityRoot(){
        // When
        Witch.bind(target, activity);

        // Then
        verify(core).doBind(same(target), viewFinderWith(activityContentView));
    }

    @Test
    public void bind_WhitView_Should_Call_WitchCore_doBind_With_Target_And_ViewBinderWithView(){
        // When
        Witch.bind(target, view);

        // Then
        verify(core).doBind(same(target), viewFinderWith(view));
    }

    @Test
    public void bind_Should_Call_WitchCore_doBind_With_Target_And_ViewBinderWithDefaultTag(){
        // When
        Witch.bind(target, view);

        // Then
        verify(core).doBind(same(target), viewFinderWithTag(Witch.VIEW_HOLDER_TAG_DEFAULT));
    }

    @Test(expected = IllegalStateException.class)
    public void bind_When_LooperNotMain_Should_ThrowIllegalStateException(){
        ((TestLooperHelper)Witch.looperHelper).isCurrentLooperMainLooper = false;
        Witch.bind(target, view);
    }

    @Test
    public void enableLogging_Should_EnableLogging(){
        // When
        Witch.setLoggingEnabled(true);

        // Then
        verify(core).setLoggingEnabled(eq(true));
    }

    @Test
    public void disableLogging_Should_DisableLogging(){
        // When
        Witch.setLoggingEnabled(false);

        // Then
        verify(core).setLoggingEnabled(eq(false));
    }

    private ViewFinder viewFinderWithTag(final int tag) {
        return argThat(new ArgumentMatcher<ViewFinder>() {
            @Override
            public boolean matches(ViewFinder argument) {
                return argument.getTag() == tag;
            }
        });
    }

    private ViewFinder viewFinderWith(final View view) {
        return argThat(new ArgumentMatcher<ViewFinder>() {
            @Override
            public boolean matches(ViewFinder argument) {
                return argument.getRoot() == view;
            }
        });
    }
}
