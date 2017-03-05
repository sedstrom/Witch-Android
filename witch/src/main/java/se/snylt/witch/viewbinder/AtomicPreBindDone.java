package se.snylt.witch.viewbinder;

import java.util.concurrent.atomic.AtomicInteger;

import se.snylt.witch.viewbinder.bindaction.PreBindDone;

public class AtomicPreBindDone implements PreBindDone {

    private final PreBindDone listener;

    AtomicInteger atomicInteger = new AtomicInteger();

    public AtomicPreBindDone(PreBindDone listener) {
        this.listener = listener;
    }

    public void waitForDone(){
        atomicInteger.incrementAndGet();
    }

    @Override
    public void done() {
        atomicInteger.decrementAndGet();
        check();
    }

    public final void check() {
        if(atomicInteger.get() == 0) {
            atomicInteger.decrementAndGet();
            listener.done();
        }
    }
}
