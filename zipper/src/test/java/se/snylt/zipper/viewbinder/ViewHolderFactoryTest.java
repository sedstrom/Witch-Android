package se.snylt.zipper.viewbinder;

import org.junit.Test;

import static junit.framework.Assert.assertSame;

public class ViewHolderFactoryTest {

    @Test
    public void createViewHolder_When_ViewHolderClassExists_Should_CreateViewHolder(){
        TestViewModel test = new TestViewModel();
        Class expected = TestViewModel_ViewHolder.class;
        Class result = new ViewHolderFactory().createViewHolder(test).getClass();
        assertSame(expected, result) ;
    }

    @Test(expected = ViewHolderNotFoundException.class)
    public void createViewHolder_When_ViewHolderClassDoesExists(){
        Object test = new Object();
        new ViewHolderFactory().createViewHolder(test).getClass();
    }

}