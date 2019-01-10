package com.example.witch.app;

import android.view.View;
import se.snylt.witch.annotations.BindData;

class SimpleBindData {

    @BindData(id = 0, view = View.class, set = "tag")
    String text = "foo";
}
