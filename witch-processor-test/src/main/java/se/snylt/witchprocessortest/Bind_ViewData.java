package se.snylt.witchprocessortest;

import android.widget.TextView;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;

class Bind_ViewData {

    @Data
    String foo() { return "foo"; }

    @Bind(id = R.id.testIdOne)
    void foo(TextView view, String foo) {

    }

    @Data
    String bar = "bar";

    @Bind(id = R.id.testIdTwo)
    void bar(TextView view, String bar) {

    }

}
