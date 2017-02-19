package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

import se.snylt.zipper.viewbinder.Binder;

public interface ViewFinder {

    View findViewById(int id);

    Object getViewHolder(Object key);

    void putViewHolder(Object key, Object viewHolder);

    Binder getBinder(Object key);

    void putBinder(Object key, Binder binder);
}
