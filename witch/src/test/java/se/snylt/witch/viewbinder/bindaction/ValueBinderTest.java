package se.snylt.witch.viewbinder.bindaction;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.assertNotEquals;

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
        assertNull(valueBinder.getValue());
    }

    @Test
    public void create_With_Value_Binder_Should_HaveBinderAndNullValue() {
        Object value = new Object();
        ValueBinder valueBinder = ValueBinder.create(value, binder);
        assertSame(binder, valueBinder.getBinder());
        assertSame(value, valueBinder.getValue());
    }

    @Test
    public void setValue_Should_UpdateValue() {
        Object value = new Object();
        Object newValue = new Object();
        ValueBinder<Object, Object> valueBinder = ValueBinder.create(value, binder);
        assertSame(value, valueBinder.getValue());
        valueBinder.setValue(newValue);
        assertSame(newValue, valueBinder.getValue());
    }

    @Test
    public void equals_Should_CompareValues() {
        assertEquals(valueBinder(null), valueBinder(null));
        assertEquals(valueBinder("123"), valueBinder("123"));
        assertNotEquals(valueBinder("123"), valueBinder("456"));
        assertNotEquals(valueBinder(null), valueBinder("123"));
        assertNotEquals(valueBinder("123"), "123");
    }

    private ValueBinder valueBinder(Object value){
        return ValueBinder.create(value, null);
    }
}