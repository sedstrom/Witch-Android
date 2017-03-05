package se.snylt.witch.viewbinder;


import org.apache.commons.lang3.ArrayUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import se.snylt.witch.viewbinder.bindaction.BindAction;
import se.snylt.witch.viewbinder.bindaction.OnBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPostBindAction;
import se.snylt.witch.viewbinder.bindaction.OnPreBindAction;

public class BindActions {

    public final List<OnPreBindAction> onPreBindActions = new ArrayList<>();

    public final List<OnBindAction> onBindActions = new ArrayList<>();

    public final List<OnPostBindAction> onPostBindActions = new ArrayList<>();

    public BindActions(BindAction ...bindActions) {
        addAll(bindActions);
    }

    public BindActions(List<BindAction> bindActions) {
        addAll(bindActions);
    }

    private void add(BindAction action) {

        if(action instanceof OnPreBindAction) {
            onPreBindActions.add((OnPreBindAction) action);
        }

        if(action instanceof OnBindAction) {
            onBindActions.add((OnBindAction) action);
        }

        if (action instanceof OnPostBindAction) {
            onPostBindActions.add((OnPostBindAction) action);
        }
    }

    public final void addAll(Collection<? extends BindAction> actions) {
        BindAction[] actionsArray = new BindAction[actions.size()];
        actions.toArray(actionsArray);
        addAll(actionsArray);
    }

    public final void addAll(BindAction[] actions) {
        for(BindAction action: actions) {
            add(action);
        }
    }

    public final void addAll(BindActions bindActions) {
        addAll(bindActions.onPreBindActions);
        addAll(bindActions.onBindActions);
        addAll(bindActions.onPostBindActions);
    }

    public BindActions applyMods(ViewBinder viewBinder, Object[] mods) {
        BindActions finalActions = createModsBindActions(viewBinder, mods);
        if(finalActions != null) {
            finalActions.addAll(this);
        } else {
            finalActions = this;
        }
        return finalActions;
    }

    private BindActions createModsBindActions( ViewBinder viewBinder, Object[] mods) {
        if(mods != null && mods.length > 0) {
            BindAction[] modActions = new BindAction[0];
            for(Object mod : mods) {
                modActions = ArrayUtils.addAll(viewBinder.getModActions(mod));
            }
            return new BindActions(modActions);
        }
        return null;
    }

}
