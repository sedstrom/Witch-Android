package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;

class Bind_InOrder {

    @Bind
    void first(){}

    @Bind
    void second(){}

    @Bind
    void third(){}
}
