package se.snylt.zipper.viewbinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.Activity;
import android.view.View;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ZipperTest {

    @Mock
    ZipperCore core;

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
        Zipper.zipper(core);
    }

    @Test
    public void bind_WhitActivity_Should_Call_ZipperCore_doBind_With_Target_And_ViewBinderWithActivityRoot(){
        // When
        Zipper.bind(target, activity);

        // Then
        verify(core).doBind(same(target), viewFinderWith(activityContentView));
    }

    @Test
    public void bind_WhitView_Should_Call_ZipperCore_doBind_With_Target_And_ViewBinderWithView(){
        // When
        Zipper.bind(target, view);

        // Then
        verify(core).doBind(same(target), viewFinderWith(view));
    }

    @Test
    public void bind_Should_Call_ZipperCore_doBind_With_Target_And_ViewBinderWithDefaultTag(){
        // When
        Zipper.bind(target, view);

        // Then
        verify(core).doBind(same(target), viewFinderWithTag(Zipper.VIEW_HOLDER_TAG_DEFAULT));
    }

    private ViewFinder viewFinderWithTag(final int tag) {
        return argThat(new ArgumentMatcher<ViewFinder>() {
            @Override
            public boolean matches(ViewFinder argument) {
                return argument.getTag() == tag;
            }
        });
    }

    @Test
    public void bind_WhitMods_Should_Call_ZipperCore_doBind_With_Mods(){
        // When
        Object mod1 = new Object();
        Object mod2 = new Object();
        Zipper.bind(target, view, mod1, mod2);

        // Then
        verify(core).doBind(same(target), viewFinderWith(view), same(mod1), same(mod2));
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
