package se.snylt.witch.viewbinder;


import org.junit.Test;

import static junit.framework.Assert.assertSame;

public class TargetViewBinderFactoryTest {

    @Test
    public void createBinder_WhenBinderClassExists_Should_CreateBinder(){
        TestViewModel test = new TestViewModel();
        Class expected = TestViewModel_ViewBinder.TestTargetViewBinder.class;
        Class result = new BinderFactory().createBinder(test).getClass();
        assertSame(expected, result) ;
    }

    @Test(expected = BinderNotFoundException.class)
    public void createBinder_When_BinderClassDoesExists(){
        Object test = new Object();
        new BinderFactory().createBinder(test).getClass();
    }

}