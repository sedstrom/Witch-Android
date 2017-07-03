package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.Binds;
import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class BindsViewModel extends TestViewModel {

    @BindTo(0)
    String field = "field";

    @Binds
    Binder<View, String> bindsField = Binder.create(new SyncOnBind<View, String>() {
        @Override
        public void onBind(View view, String s) {
            view.setTag(s);
        }
    });

    @BindTo(1)
    String method() {
        return "method";
    }

    @Binds
    Binder<View, String> bindsMethod() {
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

    @Binds
    Binder<View, String> getBindsFoo() {
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

    @Binds
    Binder<View, String> bindsBar() {
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

    @Binds
    Binder<View, String> getBindsPoo() {
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