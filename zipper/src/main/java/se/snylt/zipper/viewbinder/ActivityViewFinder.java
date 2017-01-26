package se.snylt.zipper.viewbinder;

import android.app.Activity;
import android.view.View;

import se.snylt.zipper.BindingViewHolder;

import static se.snylt.zipper.viewbinder.Zipper.VIEW_HOLDER_TAG;

public class ActivityViewFinder implements ViewFinder {

    private final Activity activity;

    public ActivityViewFinder(Activity activity) {
        this.activity = activity;
    }

    @Override
    public View findViewById(int id) {
        return activity.findViewById(id);
    }

    @Override
    public BindingViewHolder getViewHolder() {
        return (BindingViewHolder) activity.getWindow().getDecorView().getTag(VIEW_HOLDER_TAG);
    }

    @Override
    public void setViewHolder(BindingViewHolder viewHolder) {
        activity.getWindow().getDecorView().setTag(VIEW_HOLDER_TAG, viewHolder);
    }
}
