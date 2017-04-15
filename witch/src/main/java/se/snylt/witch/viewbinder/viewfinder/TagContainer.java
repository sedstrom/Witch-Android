package se.snylt.witch.viewbinder.viewfinder;

import java.util.HashMap;

import se.snylt.witch.viewbinder.TargetViewBinder;

public class TagContainer {

    private HashMap<Object, Object> viewHolders = new HashMap<>();

    private HashMap<Object, TargetViewBinder> binders = new HashMap<>();

    public Object getViewHolder(Object key) {
        return viewHolders.get(key);
    }

    public void putViewHolder(Object key, Object viewHolder) {
        viewHolders.put(key, viewHolder);
    }

    public TargetViewBinder getBinder(Object key) {
        return binders.get(key);
    }

    public void putBinder(Object key, TargetViewBinder targetViewBinder) {
        binders.put(key, targetViewBinder);
    }
}
