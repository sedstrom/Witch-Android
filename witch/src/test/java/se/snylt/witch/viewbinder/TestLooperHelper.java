package se.snylt.witch.viewbinder;

public class TestLooperHelper implements LooperHelper {

    boolean isCurrentLooperMainLooper = true;

    @Override
    public boolean isCurrentLooperMainLooper() {
        return isCurrentLooperMainLooper;
    }
}
