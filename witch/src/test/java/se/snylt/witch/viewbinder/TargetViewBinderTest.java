package se.snylt.witch.viewbinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

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
        targetViewBinder = new TargetViewBinder(Arrays.asList(viewBinderOne, viewBinderTwo), printer);
    }

    @Test
    public void bind_Should_CallBindOnEachViewBinder(){
        // When
        targetViewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(viewBinderOne).bind(same(viewHolder), same(viewFinder), same(target));
        verify(viewBinderTwo).bind(same(viewHolder), same(viewFinder), same(target));
    }

    @Test
    public void bind_When_LoggingEnabled_Should_Log(){
        // When
        Witch.setLoggingEnabled(true);
        targetViewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(printer).printTarget(same(target));
    }

    @Test
    public void bind_When_LoggingDisabled_Should_Never_Log(){
        // When
        Witch.setLoggingEnabled(false);
        targetViewBinder.bind(viewHolder, viewFinder, target);

        // Then
        verify(printer, never()).printTarget(same(target));
    }

}