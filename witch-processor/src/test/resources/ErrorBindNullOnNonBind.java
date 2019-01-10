package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.BindNull;
import android.view.View;

class ErrorBindNullOnNonBind {

    @Data
    @BindNull
    String data = "foo";
}
