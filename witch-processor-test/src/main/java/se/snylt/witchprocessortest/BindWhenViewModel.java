package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.annotations.BindWhen;

class BindWhenViewModel extends TestViewModel {

    @BindToView(id = android.R.id.button1, view = View.class, set = "contentDescription")
    @BindWhen(BindWhen.ALWAYS)
    String always = null;

    @BindToView(id = android.R.id.button2, view = View.class, set = "tag")
    @BindWhen(BindWhen.NOT_SAME)
    Object notSame = null;

    @BindToView(id = android.R.id.button3, view = View.class, set = "contentDescription")
    @BindWhen(BindWhen.NOT_EQUALS)
    String notEquals = null;

    BindWhenViewModel() {
        super(View.class);
    }
}