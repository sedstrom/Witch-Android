package com.example.zipper;

import java.util.List;

import se.snylt.zipper.annotations.BindToViewPager;


public class MainViewModel {

    @BindToViewPager(id = R.id.main_activity_view_pager, adapter = MyViewPagerAdapter.class, set = "pages")
    public final List<MyViewPagerAdapter.Page> pages;

    public MainViewModel(List<MyViewPagerAdapter.Page> pages) {
        this.pages = pages;
    }
}