package se.snylt.witch.viewbinder.viewfinder;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.view.View;

import java.util.HashMap;

import se.snylt.witch.viewbinder.TargetViewBinder;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ViewViewFinderTest {

    @Mock
    View view;

    HashMap<Object, Object> tagged;

    private final static int TAG = 666;

    private ViewViewFinder viewFinder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        viewFinder = new ViewViewFinder(view, TAG);

        makeViewTaggable();
    }

    private void makeViewTaggable() {
        tagged = new HashMap<>();

        when(view.getTag(eq(TAG))).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                return tagged.get(invocation.getArgument(0));
            }
        });

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                tagged.put(invocation.getArgument(0), invocation.getArgument(1));
                return null;
            }
        }).when(view).setTag(eq(TAG), any(Object.class));
    }

    @Test
    public void getRoot_Should_ReturnView(){
        assertSame(view, viewFinder.getRoot());
    }

    @Test
    public void getTag_Should_ReturnTag(){
        assertEquals(TAG, viewFinder.getTag());
    }

    @Test
    public void findViewById_Should_FindViewInView(){
        View viewInView = mock(View.class);
        doReturn(viewInView).when(view).findViewById(eq(1));

        assertSame(viewInView, viewFinder.findViewById(1));
    }

    @Test
    public void putViewHolder_Should_StoreViewHolderWithKey(){
        Object key = new Object();
        Object viewHolder = new Object();

        // When
        viewFinder.putViewHolder(key, viewHolder);

        // Then
        assertSame(viewHolder, viewFinder.getViewHolder(key));
    }

    @Test
    public void putBinder_Should_StoreBinderWithKey(){
        Object key = new Object();
        TargetViewBinder targetViewBinder = mock(TargetViewBinder.class);

        // When
        viewFinder.putBinder(key, targetViewBinder);

        // Then
        assertSame(targetViewBinder, viewFinder.getBinder(key));
    }

}