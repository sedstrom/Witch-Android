package se.snylt.witch.viewbinder.viewfinder;

import android.view.View;

import se.snylt.witch.viewbinder.Binder;

public interface ViewFinder {

    View getRoot();

    int getTag();

    View findViewById(int id);

    Object getViewHolder(Object key);

    void putViewHolder(Object key, Object viewHolder);

    Binder getBinder(Object key);

    void putBinder(Object key, Binder binder);
}
