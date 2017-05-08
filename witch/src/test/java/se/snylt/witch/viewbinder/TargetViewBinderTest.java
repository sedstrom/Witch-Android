package se.snylt.witch.viewbinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

public class TargetViewBinderTest {

    @Mock
    ViewBinder viewBinderOne;

    @Mock
    ViewBinder viewBinderTwo;

    private Object viewHolder = new Object();

    private Object target = new Object();

    @Mock
    ViewFinder viewFinder;

    private TargetViewBinder targetViewBinder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        targetViewBinder = new TargetViewBinder(Arrays.asList(viewBinderOne, viewBinderTwo));
    }

    @Test
    public void bind_Should_CallBindOnEachViewBinder(){
        // When
        targetViewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewBinderOne).bind(same(viewHolder), same(viewFinder), same(target));
        verify(viewBinderTwo).bind(same(viewHolder), same(viewFinder), same(target));
    }

}