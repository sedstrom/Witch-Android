package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;

class BindWithNoDataViewModel {

    @Bind(id = R.id.testIdOne)
    void bind(TextView view) {}

}
