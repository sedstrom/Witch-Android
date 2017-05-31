package se.snylt.witch.viewbinder.bindaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;

public class ValueBinderTest {

    @Mock
    Binder<Object, Object> binder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_With_Binder_Should_HaveBinderAndNullValue() {
        ValueBinder valueBinder = ValueBinder.create(binder);
        assertSame(binder, valueBinder.getBinder());
        assertNull(valueBinder.get());
    }

    @Test
    public void create_With_Value_Binder_Should_HaveBinderAndNullValue() {
        Object value = new Object();
        ValueBinder valueBinder = ValueBinder.create(value, binder);
        assertSame(binder, valueBinder.getBinder());
        assertSame(value, valueBinder.get());
    }

    @Test
    public void setValue_Should_UpdateValue() {
        Object value = new Object();
        Object newValue = new Object();
        ValueBinder<Object, Object> valueBinder = ValueBinder.create(value, binder);
        assertSame(value, valueBinder.get());
        valueBinder.set(newValue);
        assertSame(newValue, valueBinder.get());
    }
}