package se.snylt.zipper;

import java.util.HashMap;
import java.util.List;

public class BindingViewHolder {

    private HashMap<String, List<BindAction>> actions = new HashMap<>();

    public void putActions(String key, List<BindAction> actions) {
        this.actions.put(key, actions);
    }

    public List<BindAction> getActions(String key) {
        return actions.get(key);
    }
}
