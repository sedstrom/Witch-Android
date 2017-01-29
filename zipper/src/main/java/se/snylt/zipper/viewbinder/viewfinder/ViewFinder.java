package se.snylt.zipper.viewbinder.viewfinder;

import android.view.View;

import se.snylt.zipper.BindingViewHolder;

public interface ViewFinder {

    View findViewById(int id);

    BindingViewHolder getViewHolder();

    void setViewHolder(BindingViewHolder viewHolder);

}
