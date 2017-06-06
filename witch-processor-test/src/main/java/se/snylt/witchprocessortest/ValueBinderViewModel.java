package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindTo;
import se.snylt.witch.viewbinder.bindaction.Binder;
import se.snylt.witch.viewbinder.bindaction.SyncOnBind;
import se.snylt.witch.viewbinder.bindaction.ValueBinder;

public class ValueBinderViewModel extends TestViewModel {

    public final Integer VISIBILITY = View.VISIBLE;

    @BindTo(android.R.id.home)
    public final ValueBinder<View, Integer> visibility = ValueBinder.create(VISIBILITY, Binder.create(
            new SyncOnBind<View, Integer>() {
                @Override
                public void onBind(View view, Integer integer) {
                    view.setVisibility(integer);
                }
            }));

    public ValueBinderViewModel() {
        super(View.class);
    }
}
