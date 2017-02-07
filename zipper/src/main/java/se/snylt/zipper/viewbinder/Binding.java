package se.snylt.zipper.viewbinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Binding {

    private final List<ViewBinder> viewBinders;

    private final Map<Object, UnBinder> unBinders = new HashMap<>();

    public Binding(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    public List<ViewBinder> getViewBinders() {
        return viewBinders;
    }

    public UnBinder registersUser(final Object user) {
        UnBinder unBinder;
        if(!unBinders.containsKey(user)) {
            unBinder = new UnBinder(user, this);
            unBinders.put(user, unBinder);
        } else {
            unBinder = unBinders.get(user);
        }
        return unBinder;
    }

    public void unRegisterUser(Object user) {
        unBinders.remove(user);
    }
}
