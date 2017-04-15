package com.example.witch;

import com.example.witch.alwaysbind.AlwaysBindFragment;
import com.example.witch.animations.AnimationsFragment;
import com.example.witch.customview.CustomViewFragment;
import com.example.witch.login.LoginFragment;
import com.example.witch.mod.ModsFragment;
import com.example.witch.picasso.PicassoFragment;
import com.example.witch.recyclerview.RecyclerViewFragment;
import com.example.witch.responsemodel.ResponseModelFragment;
import com.example.witch.textview.TextViewFragment;
import com.example.witch.undo.UndoFragment;
import com.example.witch.value.ValueFragment;

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

import se.snylt.witch.viewbinder.Witch;

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
        items.add(newItem(ResponseModelFragment.class.getName(), "ResponseModel", "Combine response model and view model."));
        items.add(newItem(LoginFragment.class.getName(), "Login", "Example how view states can be handled for a login view"));
        items.add(newItem(UndoFragment.class.getName(), "Undo", "Example how stack fo view can be used for view history"));
        items.add(newItem(ValueFragment.class.getName(), "ValueBinder", "Use a ValueBinder to chain bind actions"));

        ExampleListFragmentViewModel model = new ExampleListFragmentViewModel(items);

        Witch.bind(model, view);
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
