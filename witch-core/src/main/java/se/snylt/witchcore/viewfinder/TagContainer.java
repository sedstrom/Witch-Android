package se.snylt.witchcore.viewfinder;

import java.util.HashMap;

import se.snylt.witchcore.TargetViewBinder;

public class TagContainer {

    private HashMap<Object, Object> viewHolders = new HashMap<>();

    private HashMap<Object, TargetViewBinder> binders = new HashMap<>();

    private ViewFinder viewFinder;

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

    public ViewFinder getViewFinder() {
        return viewFinder;
    }

    public void setViewFinder(ViewFinder viewFinder) {
        this.viewFinder = viewFinder;
    }
}
