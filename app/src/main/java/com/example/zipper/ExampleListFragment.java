package com.example.zipper;

import com.example.zipper.alwaysbind.AlwaysBindFragment;
import com.example.zipper.animations.AnimationsFragment;
import com.example.zipper.customview.CustomViewFragment;
import com.example.zipper.mod.ModsFragment;
import com.example.zipper.picasso.PicassoFragment;
import com.example.zipper.recyclerview.RecyclerViewFragment;
import com.example.zipper.textview.TextViewFragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.snylt.zipper.viewbinder.Zipper;

public class ExampleListFragment extends Fragment {

    private static final String TAG = ExampleListFragment.class.getSimpleName();

    OnExampleFragmentSelected listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof OnExampleFragmentSelected) {
            listener = (OnExampleFragmentSelected) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RecyclerView recyclerView = (RecyclerView) inflater.inflate(R.layout.example_list_fragment, container, false);
        recyclerView.setLayoutManager(new LinearLayoutManager(inflater.getContext()));
        recyclerView.setAdapter(new ExampleListRecyclerViewAdapter());
        return recyclerView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Pages in main view
        List<ExampleItem> items = new ArrayList<>();
        items.add(newItem(RecyclerViewFragment.class.getName(), "@BindToRecyclerView", "Bind items in a recycler view adapter"));
        items.add(newItem(TextViewFragment.class.getName(), "@BindToTextView", "TextView binding" ));
        items.add(newItem(AlwaysBindFragment.class.getName(), "@AlwaysBind", "Example of how to use @AlwaysBind to skip value diffing"));
        items.add(newItem(CustomViewFragment.class.getName(), "CustomView", "Bind values to a custom view class"));
        items.add(newItem(ModsFragment.class.getName(), "Mods", "Add one or more mods to an existing binding."));
        items.add(newItem(AnimationsFragment.class.getName(), "Animations", "Example of how animations can be added to binding"));
        items.add(newItem(PicassoFragment.class.getName(), "Picasso", "Example of how picasso can be used in binding"));

        ExampleListFragmentViewModel model = new ExampleListFragmentViewModel(items);

        Zipper.bind(model, view);
    }

    private ExampleItem newItem(final String name, final String title, String subtitle) {
        ExampleItem item = new ExampleItem(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onExampleFragmentSelected(name, title);
            }
        }, title, subtitle);
        return item;
    }
}
