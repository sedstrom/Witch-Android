package com.example.witch.app;

import se.snylt.witch.annotations.BindWhen;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import android.view.View;

class ErrorConflictingBindWhen {

    @Data
    @BindWhen(BindWhen.NOT_SAME)
    String text = "foo";

    @BindWhen(BindWhen.ALWAYS)
    @Bind(id = 0)
    void text(View view, String text) {

    }
}
