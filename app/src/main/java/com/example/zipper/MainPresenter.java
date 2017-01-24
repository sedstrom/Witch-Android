package com.example.zipper;

public class MainPresenter {

    MainView view;

    public void takeView(MainView view) {
        this.view = view;
        this.view.bind(new MainViewModel("Text", "Hint", R.mipmap.ic_launcher , true, true, "Welcome!"));
    }
}
