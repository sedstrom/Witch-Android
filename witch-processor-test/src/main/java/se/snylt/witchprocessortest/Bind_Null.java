package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindNull;
import se.snylt.witch.annotations.Data;

class Bind_Null {

    @BindData(id = R.id.testIdOne, view = TextView.class, set = "text")
    @BindNull
    String one = null;

    @Data
    String two = null;

    @Bind
    @BindNull
    void two(String two) { }

    @Data
    String four = null;

    @Bind
    void four(String four) {}

    @BindData(id = R.id.testIdFive, view = TextView.class, set = "text")
    String five = null;

}
