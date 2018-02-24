package se.snylt.witchcore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import se.snylt.witchcore.viewbinder.ViewBinder;
import se.snylt.witchcore.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
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

    @Mock
    TargetPrinter printer;

    private TargetViewBinder targetViewBinder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        targetViewBinder = new TargetViewBinder(Arrays.asList(viewBinderOne, viewBinderTwo)) {

            @Override
            public String describeTarget(Object o) {
                return "Test";
            }
        };
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