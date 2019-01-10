package com.example.witch.app;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

public class ListDataRepository {

    public interface Callback {
        void success(List<String> data);
    }

    public static void getData(final Callback callback) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                callback.success(createData());
            }
        }, 2000);
    }

    private static List<String> createData() {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            data.add("Data item: " + i + " : " + System.currentTimeMillis());
        }
        return data;
    }
}
