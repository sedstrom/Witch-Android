package se.snylt.witchprocessortest;

import android.support.v4.view.ViewPager;

import se.snylt.witch.annotations.BindToViewPager;

class ViewPagerViewModel extends TestViewModel {

    @BindToViewPager(id = android.R.id.button1, adapter = ViewPagerAdapter.class, set = "items")
    final Object items = new Object();

    ViewPagerViewModel() {
        super(ViewPager.class);
    }
}
