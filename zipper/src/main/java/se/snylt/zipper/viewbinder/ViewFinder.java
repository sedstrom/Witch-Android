package se.snylt.zipper.viewbinder;

import android.view.View;

public interface ViewFinder {

    View findViewById(int id);

    Object getViewHolder();

    void setViewHolder(Object viewHolder);

}
