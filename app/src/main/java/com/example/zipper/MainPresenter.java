package com.example.zipper;

public class MainPresenter {

    MainView view;

    public void takeView(MainView view) {
        this.view = view;
        this.view.bind(new MainViewModel(
                "Text hello",
                "Hint",
                R.mipmap.ic_launcher,
                true,
                "Switch text", true,
                "Welcome!"));
    }
}
