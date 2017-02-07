package se.snylt.zipper.viewbinder;

public class UnBinder {

    protected Object user;

    protected Binding binding;

    protected UnBinder(Object user, Binding binding) {
        this.user = user;
        this.binding = binding;
    }

    public void unBind() {
        binding.unRegisterUser(user);
        user = null;
        binding = null;
    }
}
