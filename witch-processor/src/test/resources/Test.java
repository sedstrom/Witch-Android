package com.example.witch.app;

import android.view.View;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;

class Test {

    @Data
    String error = "Error";

    @Bind(id = 0)
    void error(View view, String error) {

    }
}
