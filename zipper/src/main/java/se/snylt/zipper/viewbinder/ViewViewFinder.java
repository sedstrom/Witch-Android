package se.snylt.zipper.viewbinder;

import android.view.View;

import static se.snylt.zipper.viewbinder.Zipper.VIEW_HOLDER_TAG;

public class ViewViewFinder implements ViewFinder {

    private final View view;

    public ViewViewFinder(View view) {
        this.view = view;
    }

    @Override
    public View findViewById(int id) {
        return view.findViewById(id);
    }

    @Override
    public Object getViewHolder() {
        return view.getTag(VIEW_HOLDER_TAG);
    }

    @Override
    public void setViewHolder(Object viewHolder) {
        view.setTag(VIEW_HOLDER_TAG, viewHolder);
    }
}
