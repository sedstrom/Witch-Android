package se.snylt.witch.viewbinder;

import android.util.Log;

import se.snylt.witchcore.Logger;


class AndroidLogger implements Logger {

    private final String LOG_TAG = "Witch";

    @Override
    public void v(String message) {
        Log.v(LOG_TAG, message);
    }

    @Override
    public void d(String message) {
        Log.d(LOG_TAG, message);
    }

    @Override
    public void w(String message) {
        Log.w(LOG_TAG, message);
    }

    @Override
    public void e(String message) {
        Log.e(LOG_TAG, message);
    }
}
