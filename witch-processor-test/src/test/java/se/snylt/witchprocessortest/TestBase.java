package se.snylt.witchprocessortest;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import se.snylt.witch.viewbinder.TargetViewBinder;
import se.snylt.witch.viewbinder.viewbinder.ViewBinder;
import se.snylt.witchprocessortest.utils.TestViewBinderUtils;

import static se.snylt.witchprocessortest.utils.TestViewBinderUtils.verifyViewBinder;

public abstract class TestBase {

    TargetViewBinder targetViewBinder;

    Object viewHolder;



    @Before
    public void setup(){
    }

    @Test
    public void testSimpleBind(){
        testBind(getTargetViewBinder().getViewBinders(), getViewHolder());
    }

    protected void doBind(TestViewModel testViewModel, TestViewBinderUtils.VerifyPostBind verifyBind) {
        verifyViewBinder(
                getTargetViewBinder().getViewBinders(),
                getViewHolder(),
                testViewModel,
                verifyBind);
    }

    protected void doBind(TargetViewBinder binder,  TestViewModel testViewModel, TestViewBinderUtils.VerifyPostBind verifyBind) {
        verifyViewBinder(
                binder.getViewBinders(),
                getViewHolder(),
                testViewModel,
                verifyBind);
    }

    protected abstract void testBind(List<ViewBinder> binder, Object viewHolder);

    protected abstract Object getViewHolder();

    protected abstract TargetViewBinder getTargetViewBinder();

}