package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import android.view.View;

class ErrorIncompatibleDataTypes {

    @Data
    String text = "foo";

    @Bind(id = 0)
    void bind(View view, Integer text) {

    }
}
