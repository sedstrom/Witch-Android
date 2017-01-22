package com.example.zipper;

public class MainPresenter {

    MainView view;

    public void takeView(MainView view) {
        this.view = view;
        this.view.bind(new MainViewModel("Simon Edström", "Hello" , R.mipmap.ic_launcher, true, 0, true, "Hello :)"));
    }
}
