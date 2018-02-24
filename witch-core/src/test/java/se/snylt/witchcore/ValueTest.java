package se.snylt.witchcore;


import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;

public class ValueTest {

    @Test
    public void set_Should_UpdateValue(){
        String first = "foo";
        String second = "bar";
        Value<String> v = new Value<>(first);
        assertSame(first, v.get());
        v.set(second);
        assertSame(second, v.get());
    }

    @Test
    public void set_Should_FlagDirty(){
        Value<String> v = new Value<>();
        v.take();
        assertFalse(v.isDirty());
        v.set("foo");
        assertTrue(v.isDirty());
    }

    @Test
    public void get_Should_NotClearFlagDirty(){
        Value<String> v = new Value<>("foo");
        assertTrue(v.isDirty());
        v.get();
        assertTrue(v.isDirty());
    }

    @Test
    public void take_Should_ClearFlagDirty(){
        Value<String> v = new Value<>("foo");
        assertTrue(v.isDirty());
        v.take();
        assertFalse(v.isDirty());
    }

    @Test
    public void update_Should_GetValue_FlagDirty(){
        String value = "foo";
        Value<String> v = new Value<>(value);
        v.take();
        assertFalse(v.isDirty());
        String update = v.update();
        assertTrue(v.isDirty());
        assertSame(value, update);
    }

}