package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import android.view.View;

class ErrorDataMethodPrivate {

    @Data
    private String text() {
        return "foo";
    }
}
