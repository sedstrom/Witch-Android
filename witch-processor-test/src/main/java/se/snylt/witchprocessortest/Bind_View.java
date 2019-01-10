package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;

class Bind_View {

    @Bind(id = R.id.testIdOne)
    void foo(TextView view) {

    }

}
