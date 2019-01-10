package com.example.witch.app;

import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindWhen;
import android.view.View;

class ErrorBindWithIdWithoutView {

    @Bind(id = 0)
    void bind() {

    }
}
