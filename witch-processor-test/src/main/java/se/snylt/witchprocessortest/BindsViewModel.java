package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class BindsViewModel extends TestViewModel {

    @BindTo(0)
    String field = "field";

    @Bind
    Binder<View, String> bindField = Binder.create(new SyncOnBind<View, String>() {
        @Override
        public void onBind(View view, String s) {
            view.setTag(s);
        }
    });

    @BindTo(1)
    String method() {
        return "method";
    }

    @Bind
    Binder<View, String> bindMethod() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    @BindTo(2)
    String foo() {
        return "foo";
    }

    @Bind
    Binder<View, String> getBindFoo() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    @BindTo(3)
    String getBar() {
        return "bar";
    }

    @Bind
    Binder<View, String> bindBar() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    @BindTo(4)
    String getPoo() {
        return "poo";
    }

    @Bind
    Binder<View, String> getBindPoo() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    BindsViewModel() {
        super(View.class);
    }
}