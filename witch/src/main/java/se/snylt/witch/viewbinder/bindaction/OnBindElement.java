package se.snylt.witch.viewbinder.bindaction;

public class OnBindElement<Target, Value> {

    private final OnBind<Target, Value> onBind;

    private OnBindElement<Target, Value> next;

    public OnBindElement(OnBind<Target, Value> onBind) {
        this.onBind = onBind;
    }

    public OnBindElement<Target, Value> getNext(){
        return next;
    }

    public void setNext(OnBind<Target, Value> next) {
        if(this.next != null) {
            this.next.setNext(next);
        } else {
            this.next = new OnBindElement<>(next);
        }
    }

    public boolean hasNext() {
        return next != null;
    }

    public OnBind<Target, Value> getOnBind() {
        return onBind;
    }
}
