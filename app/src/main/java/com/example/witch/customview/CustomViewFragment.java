package com.example.witch.customview;

import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.viewbinder.Witch;

public class CustomViewFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CustomViewModel model = new CustomViewModel("This is a custom view");
        Witch.spellBind(model, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    static class CustomViewModel {

        @BindToView(id=R.id.custom_view_fragment_custom_view, view = CustomView.class, set = "myData")
        final String data;

        CustomViewModel(String data) {
            this.data = data;
        }
    }
}
