package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import android.view.View;

class ErrorBindMethodPrivate {

    @Data
    String text = "foo";

    @Bind(id = 0)
    private void text(View view, String text) {

    }
}
