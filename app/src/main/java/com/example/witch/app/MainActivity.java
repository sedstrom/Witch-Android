package com.example.witch.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.witch.api.Api;
import com.example.witch.R;
import com.example.witch.api.ApiCallback;
import com.example.witch.api.ApiData;
import com.example.witch.api.Result;

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
        viewModel.setFetchingData(true);
        Witch.bind(viewModel, this);
        new Api().getData(new ApiCallback<Result<List<ApiData>>>() {
            @Override
            public void onResult(Result<List<ApiData>> result) {
                viewModel.setFetchingData(false);
                viewModel.setResult(result);
                Witch.bind(viewModel, MainActivity.this);
            }
        }, success);
    }
}
