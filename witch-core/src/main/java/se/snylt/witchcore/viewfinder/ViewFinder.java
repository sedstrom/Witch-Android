package se.snylt.witchcore.viewfinder;


import se.snylt.witchcore.TargetViewBinder;

public interface ViewFinder<View> {

    View getRoot();

    int getTag();

    View findViewById(int id);

    Object getViewHolder(Object key);

    void putViewHolder(Object key, Object viewHolder);

    TargetViewBinder getBinder(Object key);

    void putBinder(Object key, TargetViewBinder targetViewBinder);
}
