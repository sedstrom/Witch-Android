package com.example.witch;


import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class Api {

    void getData(final ApiCallback<Result<List<ApiData>>> callback, final boolean success) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(success) {
                    callback.onResult(Result.value(list(50)));
                } else {
                    callback.onResult(Result.<List<ApiData>>error(new String("Something went wrong...")));
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

    interface ApiCallback<Result>{

        void onResult(Result result);
    }

    public static class Result<ResultData> {

        final ResultData value;

        final String error;

        Result(ResultData value, String error) {
            this.value = value;
            this.error = error;
        }

        public static <Data> Result<Data> value(Data value) {
            return new Result<>(value, null);
        }

        public static <Data> Result<Data> error(String message) {
            return new Result<>(null, message);
        }

    }

}
