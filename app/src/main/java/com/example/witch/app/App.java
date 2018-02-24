package com.example.witch.app;

import com.squareup.leakcanary.LeakCanary;

import android.app.Application;

import se.snylt.witch.viewbinder.Witch;

public class App extends Application{

    @Override
    public void onCreate() {
        super.onCreate();

        Witch.setLoggingEnabled(false);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}
