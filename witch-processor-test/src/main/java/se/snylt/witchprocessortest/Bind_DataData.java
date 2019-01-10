package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;

class Bind_DataData {

    @Data
    String foo() { return "foo"; }

    @Bind @BindWhen(BindWhen.ALWAYS)
    void foo(String foo, String history) {

    }

}
