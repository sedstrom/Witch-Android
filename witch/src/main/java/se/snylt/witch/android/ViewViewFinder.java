package se.snylt.witch.android;

import android.view.View;

import se.snylt.witchcore.TargetViewBinder;
import se.snylt.witchcore.viewfinder.TagContainer;
import se.snylt.witchcore.viewfinder.ViewFinder;

public class ViewViewFinder implements ViewFinder<View> {

    private final View view;

    private final int tag;

    public static ViewFinder from(View view, int tag) {
        TagContainer tagContainer = getTagContainer(view, tag);
        if(tagContainer.getViewFinder() == null) {
            tagContainer.setViewFinder(new ViewViewFinder(view, tag));
        }
        return tagContainer.getViewFinder();
    }

    private ViewViewFinder(View view, int tag) {
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
        return getTagContainer(view, tag);
    }

    private static TagContainer getTagContainer(View view, int tag) {
        TagContainer tagContainer = (TagContainer) view.getTag(tag);
        if(tagContainer == null) {
            tagContainer = new TagContainer();
            view.setTag(tag, tagContainer);
        }
        return tagContainer;
    }
}
