package com.example.witch.app;

import android.view.View;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;

class SimpleBindMethodData {

    @Data
    String text = "foo";

    @Bind
    void text(String text) {}
}
