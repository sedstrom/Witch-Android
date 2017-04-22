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

import se.snylt.witch.annotations.BindToRecyclerView;
import se.snylt.witch.viewbinder.Witch;

public class RecyclerViewFragment extends Fragment implements View.OnClickListener {

    final List<MyRecyclerViewAdapter.MyItem> items = new ArrayList<>();

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
        generateItems();
        Witch.bind(this, view);
    }

    private void generateItems() {
        items.clear();
        for(int i = 0; i < 100; i++) {
            MyRecyclerViewAdapter.MyItem item = createItem("Jane Doe", "User id: " + i, this);
            items.add(item);
        }
    }

    @BindToRecyclerView(id=R.id.recycler_view_fragment_recycler_view, adapter = MyRecyclerViewAdapter.class, set = "items")
    public List<MyRecyclerViewAdapter.MyItem> getItems() {
        return items;
    }

    private MyRecyclerViewAdapter.MyItem createItem(String title, String subtitle, View.OnClickListener listener) {
        return new MyRecyclerViewAdapter.MyItem(title, subtitle, listener);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(getContext(), "Click!", Toast.LENGTH_SHORT).show();
    }
}
