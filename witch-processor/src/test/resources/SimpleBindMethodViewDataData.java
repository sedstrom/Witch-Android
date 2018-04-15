package com.example.witch.app;

import android.view.View;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;

class SimpleBindMethodViewDataData {

    @Data
    String text() {
        return "foo";
    }

    @Bind(id = 0)
    void bind(View view, String text, String history) {}
}
