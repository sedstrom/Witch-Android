package com.example.witch.app;

import com.squareup.leakcanary.LeakCanary;
import android.app.Application;
import se.snylt.witch.viewbinder.Witch;

public class App extends Application {

    private static Login login;

    @Override
    public void onCreate() {
        super.onCreate();

        login = new Login();

        Witch.setLoggingEnabled(true);

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }

        LeakCanary.install(this);
    }

    public static Login getLogin() {
        return login;
    }
}