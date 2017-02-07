package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

public class ViewViewFinder implements ViewFinder {

    private final View view;

    private final Object user;

    private final int tag;

    public ViewViewFinder(View view, Object user, int tag) {
        this.view = view;
        this.user = user;
        this.tag = tag;
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Object getViewHolder() {
        return view.getTag(tag);
    }

    @Override
    public void setViewHolder(Object viewHolder) {
        view.setTag(tag, viewHolder);
    }

    @Override
    public Object getUser() {
        return user;
    }
}
