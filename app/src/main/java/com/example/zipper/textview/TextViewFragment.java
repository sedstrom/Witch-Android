package com.example.zipper.textview;

import com.example.zipper.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class TextViewFragment extends Fragment {

    private Binding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.text_view_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = Zipper.bind(new TextViewViewModel("This is a title", "This is a hint"), view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unBind();
    }
}
