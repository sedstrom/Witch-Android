package se.snylt.witchprocessortest;

import android.view.View;
import android.widget.TextView;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;

class BindWhenViewModel {

    @BindData(id = R.id.testIdOne, view = TextView.class, set = "text")
    @BindWhen(BindWhen.ALWAYS)
    String always = null;

    @BindData(id = R.id.testIdTwo, view = TextView.class, set = "text")
    @BindWhen(BindWhen.NOT_EQUALS)
    String notEquals = null;

    @BindData(id = R.id.testIdThree, view = TextView.class, set = "tag")
    @BindWhen(BindWhen.NOT_SAME)
    Object notSame = null;


}