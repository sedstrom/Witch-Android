package se.snylt.zipper.viewbinder.viewfinder;

import android.app.Activity;
import android.view.View;

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
    public Object getViewHolder() {
        return activity.getWindow().getDecorView().getTag(VIEW_HOLDER_TAG);
    }

    @Override
    public void setViewHolder(Object viewHolder) {
        activity.getWindow().getDecorView().setTag(VIEW_HOLDER_TAG, viewHolder);
    }
}
