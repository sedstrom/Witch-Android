package se.snylt.witchprocessortest;

import android.support.v4.view.ViewPager;

import se.snylt.witch.annotations.BindToViewPager;

public class ViewPagerViewModel extends TestViewModel {

    @BindToViewPager(id = android.R.id.button1, adapter = ViewPagerAdapter.class, set = "items")
    public final Object items = new Object();

    public ViewPagerViewModel() {
        super(ViewPager.class);
    }
}
