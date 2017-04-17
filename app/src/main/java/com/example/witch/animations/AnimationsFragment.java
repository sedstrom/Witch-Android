package com.example.witch.animations;

import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.snylt.witch.viewbinder.Witch;
import se.snylt.witch.viewbinder.bindaction.Binder;

public class AnimationsFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.animations_fragment, container, false);

        view.findViewById(R.id.animations_fragment_bind_flip_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(flipBinder());
            }
        });

        view.findViewById(R.id.animations_fragment_bind_slide_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(slideBinder());
            }
        });

        view.findViewById(R.id.animations_fragment_bind_both_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bind(flipAndSlideBinder());
            }
        });

        return view;
    }

    private void bind(Binder<TextView, String> binder) {
        Witch.bind(randomNumbersViewModel(binder), getView());
    }

    private AnimationsViewModel randomNumbersViewModel(Binder<TextView, String> binder) {
        return new AnimationsViewModel(
                randomNumberString(),
                randomNumberString(),
                randomNumberString(),
                randomNumberString(),
                binder);
    }

    private String randomNumberString() {
        Double d = (Math.random() * 100000d);
        return "" + d.intValue();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bind(flipBinder());
    }

    private Binder<TextView, String> flipBinder() {
        return Binder
                .create(new FlipOut<TextView, String>())
                .next(new SetText())
                .next(new FlipIn<TextView, String>());
    }

    private Binder<TextView, String> slideBinder() {
        return Binder
                .create(new SlideOut<TextView, String>())
                .next(new SetText())
                .next(new SlideIn<TextView, String>());
    }

    private Binder<TextView, String> flipAndSlideBinder() {
        return Binder
                .create(new FlipOut<TextView, String>(), new SlideOut<TextView, String>())
                .next(new SetText())
                .next(new FlipIn<TextView, String>(), new SlideIn<TextView, String>());
    }
}
