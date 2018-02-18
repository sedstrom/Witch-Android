package com.example.witch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

public class MainActivity extends AppCompatActivity {

    MainActivityViewModel viewModel = new MainActivityViewModel();

    @Data
    MainActivity setup = this;

    @Bind(id = R.id.list)
    void setup(RecyclerView list, MainActivity activity) {
        list.setLayoutManager(new LinearLayoutManager(activity));
        list.setAdapter(new RecyclerViewBinderAdapter.Builder<ApiData>()
        .binder(new DataBinder())
        .build());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Witch.bind(this, this);
        Witch.bind(viewModel, this);
    }

    void updateSuccess(View button) {
        fetch(true);
    }

    void updateFail(View button) {
        fetch(false);
    }

    private void fetch(boolean success) {
        viewModel.fetchingData = true;
        Witch.bind(viewModel, this);
        new Api().getData(new Api.ApiCallback<Api.Result<List<ApiData>>>() {
            @Override
            public void onResult(Api.Result<List<ApiData>> result) {
                viewModel.fetchingData = false;
                viewModel.apiResult = result;
                Witch.bind(viewModel, MainActivity.this);
            }
        }, success);
    }
}
