package com.example.witch.api;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class Api {

    public void getData(final ApiCallback<Result<List<ApiData>>> callback, final boolean success) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(success) {
                    callback.onResult(Result.value(list(50)));
                } else {
                    callback.onResult(Result.<List<ApiData>>error("Something went wrong..."));
                }

            }
        }, 2000);
    }

    private List<ApiData> list(int count) {
        List<ApiData> list = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            list.add(new ApiData(i, "A simple data object"));
        }
        return list;
    }

}
