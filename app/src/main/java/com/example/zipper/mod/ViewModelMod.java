package com.example.zipper.mod;

import java.util.Arrays;
import java.util.List;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.bindaction.BindAction;

@Mod(ViewModel.class)
public class ViewModelMod {

    // Same field name as in view model
    public final List<BindAction> text;

    public ViewModelMod(BindAction ...action) {
        this.text = Arrays.asList(action);
    }
}
