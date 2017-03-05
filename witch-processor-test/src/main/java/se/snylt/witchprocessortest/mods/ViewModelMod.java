package se.snylt.witchprocessortest.mods;

import se.snylt.witch.annotations.Mod;
import se.snylt.witch.viewbinder.bindaction.BindAction;
import se.snylt.witchprocessortest.bindactions.TestOnBindAction;

@Mod(ModViewModel.class)
public class ViewModelMod {

    public final BindAction[] something = new BindAction[]{new TestOnBindAction()};

}
