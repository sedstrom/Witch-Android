package se.snylt.zipper.viewbinder;

public class NewBinding {

    protected Object user;

    protected Binder binder;

    private Object target;

    protected NewBinding(Object user, Binder binder) {
        this.user = user;
        this.binder = binder;
    }

    public void unBind() {
        binder.unRegisterUser(user);
        user = null;
        binder = null;
        target = null;
    }

    public void setTarget(Object target) {
        this.target = target;
    }

    public Object getTarget() {
        return target;
    }
}
