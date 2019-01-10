package com.example.witch.app;

import android.view.View;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Data;

class SimpleBindMethodView {

    @Bind(id = 0)
    void text(View view) {}
}
