package se.snylt.witchprocessortest;

import android.view.View;
import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class BindViewModel extends TestViewModel {

    /*
    String field = "field";

    @Bind(id = 0)
    Binder<View, String> bindsField = Binder.create(new SyncOnBind<View, String>() {
        @Override
        public void onBind(View view, String s) {
            view.setTag(s);
        }
    });

    String method() {
        return "method";
    }

    @Bind(id = 1)
    Binder<View, String> method(TextView view, String method) {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    String foo() {
        return "foo";
    }

    @Bind
    Binder<View, String> getBindsFoo() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    String getBar() {
        return "bar";
    }

    @Bind
    Binder<View, String> bindsBar() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    String getPoo() {
        return "poo";
    }

    @Bind
    Binder<View, String> getBindsPoo() {
        return Binder.create(new SyncOnBind<View, String>() {
            @Override
            public void onBind(View view, String s) {
                view.setTag(s);
            }
        });
    }

    */
    BindViewModel() {
        super(View.class);
    }
}