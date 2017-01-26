package se.snylt.zipper.viewbinder;

import android.view.View;

import se.snylt.zipper.BindingViewHolder;

public interface ViewFinder {

    View findViewById(int id);

    BindingViewHolder getViewHolder();

    void setViewHolder(BindingViewHolder viewHolder);

}
