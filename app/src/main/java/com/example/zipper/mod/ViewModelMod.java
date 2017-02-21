package com.example.zipper.mod;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.bindaction.BindAction;

@Mod(ViewModel.class)
public class ViewModelMod {

    public final BindAction[] text;

    public ViewModelMod(BindAction ...action) {
        this.text = action;
    }
}
