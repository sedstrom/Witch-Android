package se.snylt.witch.viewbinder;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import se.snylt.witch.viewbinder.viewfinder.ViewFinder;

import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.verify;

public class BinderTest {

    @Mock
    ViewBinder viewBinderOne;

    @Mock
    ViewBinder viewBinderTwo;

    Object viewHolder = new Object();

    Object target = new Object();

    Object mod = new Object();

    @Mock
    ViewFinder viewFinder;

    private Binder binder;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        binder = new Binder(Arrays.asList(viewBinderOne, viewBinderTwo));
    }

    @Test
    public void bind_Should_CallBindOnEachViewBinder(){
        // When
        binder.bind(viewHolder, viewFinder, target, mod);

        // Then
        verify(viewBinderOne).bind(same(viewHolder), same(viewFinder), same(target), same(mod));
        verify(viewBinderTwo).bind(same(viewHolder), same(viewFinder), same(target), same(mod));
    }

}