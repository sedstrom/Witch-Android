package com.example.zipper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import se.snylt.zipper.viewbinder.Zipper;

public class MainActivity extends AppCompatActivity implements MainView {

    MainPresenter presenter = new MainPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter.takeView(this);
    }

    @Override
    public void bind(MainViewModel viewModel) {
        Zipper.bind(viewModel, this);
    }
}
