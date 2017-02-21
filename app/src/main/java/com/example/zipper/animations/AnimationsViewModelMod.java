package com.example.zipper.animations;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.bindaction.BindAction;

@Mod(AnimationsViewModel.class)
public class AnimationsViewModelMod {

    public final BindAction[] one;

    public final BindAction[] two;

    public final BindAction[] three;

    public final BindAction[] four;

    public AnimationsViewModelMod(BindAction ...mods){
        one = mods;
        two = mods;
        three = mods;
        four = mods;
    }

}
