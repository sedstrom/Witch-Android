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
        CustomViewModel model = new CustomViewModel("@BindView\nid = R.id.123, view = CustomView.class, set = \"property\"");
        Witch.bind(model, view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public static class CustomViewModel {

        @BindToView(id=R.id.custom_view_fragment_custom_view, view = CustomView.class, set = "myData")
        public final String data;

        public CustomViewModel(String data) {
            this.data = data;
        }
    }
}
