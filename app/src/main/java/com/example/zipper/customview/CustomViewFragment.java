package com.example.zipper.customview;

import com.example.zipper.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.snylt.zipper.annotations.BindToView;
import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class CustomViewFragment extends Fragment {

    private Binding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.custom_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = Zipper.bind(new CustomViewModel("@BindView\nid = R.id.123, view = CustomView.class, set = \"property\""), view);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unBind();
    }

    public static class CustomViewModel {

        @BindToView(id=R.id.custom_view_fragment_custom_view, view = CustomView.class, set = "myData")
        public final String data;

        public CustomViewModel(String data) {
            this.data = data;
        }
    }
}
