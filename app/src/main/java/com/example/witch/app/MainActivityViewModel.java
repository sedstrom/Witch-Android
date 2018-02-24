package com.example.witch.app;

import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.example.witch.R;
import com.example.witch.api.ApiData;
import com.example.witch.api.Result;

import java.util.List;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindNull;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

class MainActivityViewModel {

    private boolean fetchingData = false;

    private String error = null;

    private List<ApiData> data = null;

    void setResult(Result<List<ApiData>> result) {
        data = result.value;
        error = result.error != null ? result.error + "" : null;
    }

    void setFetchingData(boolean fetchingData) {
        this.fetchingData = fetchingData;
    }

    @Data
    String error() {
        return error;
    }

    @Bind(id = R.id.list)
    @BindWhen(BindWhen.NOT_SAME)
    void error(View list, String error) {
        Toast.makeText(list.getContext(), error, Toast.LENGTH_LONG).show();
    }

    @BindData(id = R.id.progress, view = ProgressBar.class, set="visibility")
    int progressVisibility() {
        return fetchingData && !hasData() ? View.VISIBLE : View.INVISIBLE;
    }

    @BindData(id = R.id.small_progress, view = ProgressBar.class, set="visibility")
    int smallProgressVisibility() {
        return fetchingData && hasData() ? View.VISIBLE : View.INVISIBLE;
    }

    @BindData(id = R.id.list, view = RecyclerView.class, set="visibility")
    int listVisibility() {
        return hasData() ? View.VISIBLE : View.INVISIBLE;
    }

    @Data
    boolean buttonsEnabled() {
        return !fetchingData;
    }

    @Bind(id = R.id.buttonPanel)
    void buttonsEnabled(View view, boolean enabled) {
        if(enabled) {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0)
                    .setDuration(300)
                    .start();
        } else {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.getMeasuredHeight())
                    .setDuration(300)
                    .start();
        }
    }

    private boolean hasData() {
        return data != null && data.size() > 0;
    }

    @Data
    List<ApiData> data() {
        return data;
    };

    @BindNull
    @Bind(id = R.id.list)
    void data(RecyclerView list, List<ApiData> data) {
        RecyclerViewBinderAdapter<ApiData> adapter = (RecyclerViewBinderAdapter<ApiData>) list.getAdapter();
        adapter.setItems(data);
        adapter.notifyDataSetChanged();
    }
}
