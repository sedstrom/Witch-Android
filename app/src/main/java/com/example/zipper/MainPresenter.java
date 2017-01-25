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
                "Welcome!", generateItems(1000)));
    }

    private MyRecyclerViewAdapter.MyItem[] generateItems(int count) {
        MyRecyclerViewAdapter.MyItem[] items = new MyRecyclerViewAdapter.MyItem[count];
        for(int i = 0; i < count; i++) {
            items[i] = new MyRecyclerViewAdapter.MyItem("John " + i, "Doe");
        }
        return items;
    }
}
