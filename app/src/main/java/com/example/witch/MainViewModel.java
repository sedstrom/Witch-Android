package com.example.witch;

import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

public class MainViewModel {

    @BindTo(R.id.main_activity_fragment_container)
    public final ValueBinder<View, Fragment> fragment;

    @BindTo(R.id.main_activity_toolbar)
    public final boolean displayHomeAsUp;

    @BindToView(id = R.id.main_activity_toolbar, view = Toolbar.class, set = "title")
    public final String title;

    public MainViewModel(Fragment fragment, Binder<View, Fragment> showFragment, boolean displayHomeAsUp, String title) {
        this.fragment = ValueBinder.create(fragment, showFragment);
        this.displayHomeAsUp = displayHomeAsUp;
        this.title = title;
    }


}
