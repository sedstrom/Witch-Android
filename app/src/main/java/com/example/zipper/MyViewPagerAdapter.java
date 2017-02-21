package com.example.zipper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<Page> pages;

    private final Context context;

    public MyViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    public void setPages(List<Page> pages) {
        this.pages = pages;
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).title;
    }

    @Override
    public int getCount() {
        return pages == null ? 0 : pages.size();
    }

    @Override
    public Fragment getItem(int position) {
        return Fragment.instantiate(context, pages.get(position).fragment);
    }

    public static class Page {

        public final String fragment;

        public final String title;

        public Page(String fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }
    }
}
