package se.snylt.witch.viewbinder.viewfinder;

import android.view.View;

import se.snylt.witch.viewbinder.TargetViewBinder;

public class ViewViewFinder implements ViewFinder {

    private final View view;

    private final int tag;

    public ViewViewFinder(View view, int tag) {
        this.view = view;
        this.tag = tag;
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
