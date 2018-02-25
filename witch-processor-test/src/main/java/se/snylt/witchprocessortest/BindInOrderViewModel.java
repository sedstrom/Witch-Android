package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.BindData;

class BindInOrderViewModel {

    @BindData(id = R.id.testIdOne, view = TextView.class, set="text")
    final String first = "first";

    @BindData(id = R.id.testIdTwo, view = TextView.class, set="text")
    final String second = "second";

    @BindData(id = R.id.testIdThree, view = TextView.class, set="text")
    final String third = "third";
}
