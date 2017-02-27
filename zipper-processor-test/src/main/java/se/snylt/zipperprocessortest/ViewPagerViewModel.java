package se.snylt.zipperprocessortest;

import android.support.v4.view.ViewPager;

import se.snylt.zipper.annotations.BindToViewPager;

public class ViewPagerViewModel extends TestViewModel {

    @BindToViewPager(id = 1, adapter = ViewPagerAdapter.class, set = "items")
    public final Object items = new Object();

    public ViewPagerViewModel() {
        super(ViewPager.class);
    }
}
