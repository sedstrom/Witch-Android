package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

import static se.snylt.zipper.viewbinder.Zipper.VIEW_HOLDER_TAG;

public class ViewViewFinder implements ViewFinder {

    private final View view;

    private final Object user;

    public ViewViewFinder(View view, Object user) {
        this.view = view;
        this.user = user;
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Object getViewHolder() {
        return  view.getTag(VIEW_HOLDER_TAG);
    }

    @Override
    public void setViewHolder(Object viewHolder) {
        view.setTag(VIEW_HOLDER_TAG, viewHolder);
    }

    @Override
    public Object getUser() {
        return user;
    }
}
