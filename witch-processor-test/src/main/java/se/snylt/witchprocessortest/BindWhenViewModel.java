package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;

class BindWhenViewModel extends TestViewModel {

    @BindData(id = android.R.id.button1, view = View.class, set = "contentDescription")
    @BindWhen(BindWhen.ALWAYS)
    String always = null;

    @BindData(id = android.R.id.button2, view = View.class, set = "tag")
    @BindWhen(BindWhen.NOT_SAME)
    Object notSame = null;

    @BindData(id = android.R.id.button3, view = View.class, set = "contentDescription")
    @BindWhen(BindWhen.NOT_EQUALS)
    String notEquals = null;

    BindWhenViewModel() {
        super(View.class);
    }
}