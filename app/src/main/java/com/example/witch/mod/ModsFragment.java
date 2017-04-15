package com.example.witch.mod;

import com.example.witch.R;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import se.snylt.witch.viewbinder.Witch;

public class ModsFragment extends Fragment {

    private ViewModelMod red = new ViewModelMod(new TextColor(Color.rgb(255,80,80)));

    private ViewModelMod blue = new ViewModelMod(new TextColor(Color.rgb(80,80,255)));

    private ViewModelMod fadeIn = new ViewModelMod(new TextColor(Color.rgb(255,80,255)), new FadeIn());

    private ViewModel model = new ViewModel();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mods_fragment, container, false);

        view.findViewById(R.id.mods_fragment_red_mod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.text = "RED";
                bind(red);
            }
        });

        view.findViewById(R.id.mods_fragment_blue_mod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.text = "BLUE";
                bind(blue);
            }
        });

        view.findViewById(R.id.mods_fragment_fade_in_mod).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                model.text = "MAGENTA";
                bind(fadeIn);
            }
        });
        return view;
    }

    private void bind(Object mod) {
        Witch.bind(model, getView());
    }
}
