package se.snylt.zipper.viewbinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public class Binder {

    private final List<ViewBinder> viewBinders;

    private final Map<Object, Binding> users = new HashMap<>();

    private BindingAbandonedListener bindingAbandonedListener;

    public Binder(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    private Binding getBinding(Object user) {
        Binding binding;
        if(!users.containsKey(user)) {
            binding = new Binding(user, this);
            users.put(user, binding);
        } else {
            binding = users.get(user);
        }
        return binding;
    }

    public void unRegisterUser(Object user) {
        users.remove(user);
        if(users.size() == 0) {
            this.bindingAbandonedListener.onBindingAbandoned();
        }
    }

    public void setOnBindingAbandonedListener(BindingAbandonedListener bindingAbandonedListener) {
        this.bindingAbandonedListener = bindingAbandonedListener;
    }

    public Binding bind(Object viewHolder, ViewFinder viewFinder, Object target, Object ...mods) {
        Binding binding = getBinding(viewFinder.getUser());
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target, mods);
        }
        return binding;
    }
}