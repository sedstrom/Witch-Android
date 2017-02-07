package se.snylt.zipper.viewbinder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import se.snylt.zipper.viewbinder.viewfinder.ViewFinder;

public class Binder {

    private final List<ViewBinder> viewBinders;

    private final Map<Object, NewBinding> users = new HashMap<>();

    private BindingAbandonedListener bindingAbandonedListener;

    public Binder(List<ViewBinder> viewBinders) {
        this.viewBinders = viewBinders;
    }

    private NewBinding getBinding(Object user) {
        NewBinding binding;
        if(!users.containsKey(user)) {
            binding = new NewBinding(user, this);
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

    public NewBinding bind(Object viewHolder, ViewFinder viewFinder, Object target) {
        NewBinding binding = getBinding(viewFinder.getUser());
        for (ViewBinder viewBinder : viewBinders) {
            viewBinder.bind(viewHolder, viewFinder, target, binding.getTarget());
        }
        binding.setTarget(target);
        return binding;
    }
}
;