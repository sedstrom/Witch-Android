package com.example.zipper.animations;

import java.util.Arrays;
import java.util.List;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.bindaction.BindAction;

@Mod(AnimationsViewModel.class)
public class AnimationsViewModelMod {

    public final List<BindAction> one;

    public final List<BindAction> two;

    public final List<BindAction> three;

    public final List<BindAction> four;

    public AnimationsViewModelMod(BindAction ...mod){
        one = Arrays.asList(mod);
        two = one;
        three = one;
        four = one;
    }

}
