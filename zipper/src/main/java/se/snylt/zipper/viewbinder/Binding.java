package se.snylt.zipper.viewbinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public class Binding {

    private final List<ViewBinder> viewBinders;

    private final Map<Object, UnBinder> unBinders = new HashMap<>();

    private BindingAbandonedListener bindingAbandonedListener;

    public Binding(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    private UnBinder registersUser(final Object user) {
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
        if(unBinders.size() == 0) {
            this.bindingAbandonedListener.onBindingAbandoned();
        }
    }

    public void setOnBindingAbandonedListener(BindingAbandonedListener bindingAbandonedListener) {
        this.bindingAbandonedListener = bindingAbandonedListener;
    }

    public UnBinder bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        UnBinder unBinder = registersUser(viewFinder.getUser());
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target);
        }
        return unBinder;
    }
}
