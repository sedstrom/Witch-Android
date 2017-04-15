package com.example.witch.animations;

import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import se.snylt.witch.viewbinder.Witch;

public class AnimationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animations_fragment, container, false);
        view.findViewById(R.id.animations_fragment_bind_flip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(new AnimationsViewModelMod(new FlipMod()));
            }
        });

        view.findViewById(R.id.animations_fragment_bind_slide_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(new AnimationsViewModelMod(new SlideMod()));
            }
        });

        view.findViewById(R.id.animations_fragment_bind_both_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(new AnimationsViewModelMod(new FlipMod(), new SlideMod()));
            }
        });

        return view;
    }

    private void bind(Object ...mod) {
        Witch.bind(randomNumbersViewModel(), getView());
    }

    private AnimationsViewModel randomNumbersViewModel() {
        return new AnimationsViewModel(randomNumberString(), randomNumberString(), randomNumberString(), randomNumberString());
    }

    private String randomNumberString() {
        Double d = (Math.random()*100000d);
        return "" + d.intValue();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind();
    }
}
