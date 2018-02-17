package se.snylt.witch.android;

import android.os.Looper;

class AndroidLooperHelper implements LooperHelper {

    @Override
    public boolean isCurrentLooperMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
