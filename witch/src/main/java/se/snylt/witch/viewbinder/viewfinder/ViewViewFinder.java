package se.snylt.witch.viewbinder.viewfinder;

import android.view.View;

import se.snylt.witch.viewbinder.TargetViewBinder;

public class ViewViewFinder implements ViewFinder {

    private View view;

    private int tag;

    private static ViewViewFinder INSTANCE;

    private ViewViewFinder() {}

    public static ViewFinder from(View view, int tag) {
        if(INSTANCE == null) {
            INSTANCE = new ViewViewFinder();
        }
        return INSTANCE.init(view, tag);
    }

    private ViewFinder init(View view, int tag) {
        this.view = view;
        this.tag = tag;
        return this;
    }

    @Override
    public View getRoot() {
        return view;
    }

    @Override
    public int getTag() {
        return tag;
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Object getViewHolder(Object key) {
        return getTagContainer().getViewHolder(key);
    }

    @Override
    public void putViewHolder(Object key, Object viewHolder) {
        getTagContainer().putViewHolder(key, viewHolder);
    }

    @Override
    public TargetViewBinder getBinder(Object key) {
        return getTagContainer().getBinder(key);
    }

    @Override
    public void putBinder(Object key, TargetViewBinder targetViewBinder) {
        getTagContainer().putBinder(key, targetViewBinder);
    }

    private TagContainer getTagContainer() {
        TagContainer tagContainer = (TagContainer) view.getTag(tag);
        if(tagContainer == null) {
            tagContainer = new TagContainer();
            view.setTag(tag, tagContainer);
        }
        return tagContainer;
    }
}
