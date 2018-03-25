package se.snylt.witch.android;

public class TestLooperHelper implements LooperHelper {

    boolean isCurrentLooperMainLooper = true;

    @Override
    public boolean isCurrentLooperMainLooper() {
        return isCurrentLooperMainLooper;
    }
}
