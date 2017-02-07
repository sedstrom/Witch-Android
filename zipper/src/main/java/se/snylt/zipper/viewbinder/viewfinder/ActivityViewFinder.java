package se.snylt.zipper.viewbinder.viewfinder;

import android.app.Activity;
import android.view.View;

public class ActivityViewFinder implements ViewFinder {

    private final Activity activity;

    private final Object user;

    private final int tag;

    public ActivityViewFinder(Activity activity, Object user, int tag) {
        this.activity = activity;
        this.user = user;
        this.tag = tag;
    }

    @Override
    public View findViewById(int id) {
        return activity.findViewById(id);
    }

    @Override
    public Object getViewHolder() {
        return activity.getWindow().getDecorView().getTag(tag);
    }

    @Override
    public void setViewHolder(Object viewHolder) {
        activity.getWindow().getDecorView().setTag(tag, viewHolder);
    }

    @Override
    public Object getUser() {
        return user;
    }
}
