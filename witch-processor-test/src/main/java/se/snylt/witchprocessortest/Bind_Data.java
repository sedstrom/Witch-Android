package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;

class Bind_Data {

    @Data
    String foo() { return "foo"; }

    @Bind
    void foo(String foo) {

    }

}
