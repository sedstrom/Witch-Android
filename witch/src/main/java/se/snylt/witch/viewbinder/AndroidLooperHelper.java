package se.snylt.witch.viewbinder;

import android.os.Looper;

class AndroidLooperHelper implements LooperHelper {

    @Override
    public boolean isCurrentLooperMainLooper() {
        return Looper.myLooper() == Looper.getMainLooper();
    }
}
