package com.example.zipper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class MainActivity extends AppCompatActivity {

    private Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_activity_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyRecyclerViewAdapter());

        binding = Zipper.bind(new MainViewModel("My title", "My hint", R.mipmap.ic_launcher, generateItems(100)), this);
    }

    private MyRecyclerViewAdapter.MyItem[] generateItems(int count) {
        MyRecyclerViewAdapter.MyItem[] items = new MyRecyclerViewAdapter.MyItem[count];
        for(int i = 0; i < count; i++) {
            items[i] = new MyRecyclerViewAdapter.MyItem("John " + i, "Doe");
        }
        return items;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(binding != null) {
            binding.unBind();
        }
    }
}
