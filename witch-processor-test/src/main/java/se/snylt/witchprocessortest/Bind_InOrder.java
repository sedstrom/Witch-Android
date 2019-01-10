package se.snylt.witchprocessortest;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Setup;

class Bind_InOrder {

    @Bind
    void third(){}

    @Setup
    void first(){}

    @Bind
    void fourth(){}

    @Setup
    void second(){}
}
