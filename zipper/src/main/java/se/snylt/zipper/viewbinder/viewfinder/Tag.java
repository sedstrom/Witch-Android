package se.snylt.zipper.viewbinder.viewfinder;

import java.util.HashMap;

import se.snylt.zipper.viewbinder.Binder;

public class Tag {

    private HashMap<Object, Object> viewHolders = new HashMap<>();

    private HashMap<Object, Binder> binders = new HashMap<>();

    public Object getViewHolder(Object key) {
        return viewHolders.get(key);
    }

    public void putViewHolder(Object key, Object viewHolder) {
        viewHolders.put(key, viewHolder);
    }

    public Binder getBinder(Object key) {
        return binders.get(key);
    }

    public void putBinder(Object key, Binder binder) {
        binders.put(key, binder);
    }
}
