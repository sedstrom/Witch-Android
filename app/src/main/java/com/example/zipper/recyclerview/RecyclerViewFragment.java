package com.example.zipper.recyclerview;


import com.example.zipper.R;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import se.snylt.zipper.viewbinder.Binding;
import se.snylt.zipper.viewbinder.Zipper;

public class RecyclerViewFragment extends Fragment {

    private Binding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_fragment, container, false);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view_fragment_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyRecyclerViewAdapter());
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = Zipper.bind(createModel(), view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding.unBind();
    }

    private RecyclerViewViewModel createModel() {
        List<MyRecyclerViewAdapter.MyItem> items = new ArrayList<>();
        for(int i = 0; i < 100; i++) {
            items.add(new MyRecyclerViewAdapter.MyItem("Jane Doe", "User id: " + i));
        }
        return new RecyclerViewViewModel(items);
    }
}
