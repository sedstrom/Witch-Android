package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.annotations.Binds;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;

class BindsViewModel extends TestViewModel {

    @BindTo(0)
    String field = "field";

    @Binds
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

    @Binds
    Binder<View, String> bindMethod() {
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