package se.snylt.witchprocessortest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import se.snylt.witch.viewbinder.Binder;
import se.snylt.witch.viewbinder.ViewBinder;

public abstract class TestBase {

    private Binder binder;

    private Object viewHolder;

    @Before
    public void setup(){
        binder = getBinder();
        viewHolder = getViewHolder();
    }

    @Test
    public void testSimpleBind(){
        testBind(getBinder().getViewBinders(), getViewHolder());
    }

    protected abstract void testBind(List<ViewBinder> binder, Object viewHolder);

    protected abstract Object getViewHolder();

    protected abstract Binder getBinder();

}