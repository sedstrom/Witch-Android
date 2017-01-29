package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

import se.snylt.zipper.BindingViewHolder;

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
    public BindingViewHolder getViewHolder() {
        return (BindingViewHolder) view.getTag(VIEW_HOLDER_TAG);
    }

    @Override
    public void setViewHolder(BindingViewHolder viewHolder) {
        view.setTag(VIEW_HOLDER_TAG, viewHolder);
    }
}
