package se.snylt.zipperprocessortest.mods;

import se.snylt.zipper.annotations.Mod;
import se.snylt.zipper.viewbinder.bindaction.BindAction;
import se.snylt.zipperprocessortest.bindactions.TestOnBindAction;

@Mod(ModViewModel.class)
public class ViewModelMod {

    public final BindAction[] something = new BindAction[]{new TestOnBindAction()};

}
