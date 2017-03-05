package com.example.witch.mod;

import se.snylt.witch.annotations.Mod;
import se.snylt.witch.viewbinder.bindaction.BindAction;

@Mod(ViewModel.class)
public class ViewModelMod {

    public final BindAction[] text;

    public ViewModelMod(BindAction ...action) {
        this.text = action;
    }
}
