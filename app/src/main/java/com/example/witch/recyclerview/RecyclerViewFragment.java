package com.example.witch.recyclerview;


import com.example.witch.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import se.snylt.witch.viewbinder.recyclerview.RecyclerViewBinderAdapter;

public class RecyclerViewFragment extends Fragment implements View.OnClickListener {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        // Provide one binder per model type
        RecyclerViewBinderAdapter<Post> adapter = new RecyclerViewBinderAdapter<>(
                posts(),
                new Post.Binder(),
                new PostWithPicture.Binder());

        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Click!", Toast.LENGTH_SHORT).show();
    }

    private List<Post> posts() {
        List<Post> items = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            if(i % 2 == 0) {
                items.add(new PostWithPicture("Title" + i, "Subtitle" + i, this, "http://lorempixel.com/200/200/sports/" + i));
            } else {
                items.add(new Post("Title" + i, "Subtitle" + i));
            }
        }
        return items;
    }

}
