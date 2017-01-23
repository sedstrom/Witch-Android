package com.example.zipper;

public class MainPresenter {

    MainView view;

    public void takeView(MainView view) {
        this.view = view;
        this.view.bind(new MainViewModel("Simon Edström", R.string.app_name, R.mipmap.ic_launcher , "Welcome!"));
    }
}
