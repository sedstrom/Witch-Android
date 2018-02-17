package com.example.witch;

import java.util.List;

public class ExampleListFragmentViewModel {

    public final List<ExampleItem> items;

    public String fragment;

    public ExampleListFragmentViewModel(List<ExampleItem> items) {
        this.items = items;
    }
}