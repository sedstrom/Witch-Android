package se.snylt.witch.viewbinder;

class AndroidLooperHelper implements LooperHelper {

    @Override
    public boolean isCurrentLooperMainLooper() {
        return false;
    }
}
