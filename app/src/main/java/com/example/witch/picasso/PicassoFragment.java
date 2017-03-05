package com.example.witch.picasso;


import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.snylt.witch.viewbinder.Witch;

public class PicassoFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.picasso_fragment, container, false);

        view.findViewById(R.id.picasso_fragment_button_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Witch.bind(new PicassoViewModel("https://www.android.com/static/img/android.png"), getView());
            }
        });

        view.findViewById(R.id.picasso_fragment_button_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Witch.bind(new PicassoViewModel("https://www.google.se/images/branding/googleg/1x/googleg_standard_color_128dp.png"), getView());
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
