package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import android.view.View;

class PrimitiveDataType {

    @BindData(id = 0, view = View.class, set = "tag")
    int somethign = 1;

    @Data
    int number = 1;

    @Bind(id = 0)
    void number(View view, Integer number) {

    }
}
