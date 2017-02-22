package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

import se.snylt.zipper.viewbinder.Binder;

public class ViewViewFinder implements ViewFinder {

    private final View view;

    private final int tag;

    public ViewViewFinder(View view, int tag) {
        this.view = view;
        this.tag = tag;
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Object getViewHolder(Object key) {
        return getTag().getViewHolder(key);
    }

    @Override
    public void putViewHolder(Object key, Object viewHolder) {
        getTag().putViewHolder(key, viewHolder);
    }

    @Override
    public Binder getBinder(Object key) {
        return getTag().getBinder(key);
    }

    @Override
    public void putBinder(Object key, Binder binder) {
        getTag().putBinder(key, binder);
    }

    private Tag getTag() {
        Tag tag = (Tag) view.getTag(this.tag);
        if(tag == null) {
            tag  = new Tag();
            view.setTag(this.tag, tag);
        }
        return tag;
    }
}
