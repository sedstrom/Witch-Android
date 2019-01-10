package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;

class Bind_ViewDataData {

    @Data
    String foo() { return "foo"; }

    @Bind(id = R.id.testIdOne) @BindWhen(BindWhen.ALWAYS)
    void foo(TextView view, String foo, String history) {
        view.setText(foo);
    }

}
