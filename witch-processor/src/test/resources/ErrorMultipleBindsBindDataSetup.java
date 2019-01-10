package com.example.witch.app;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.Setup;

import android.view.View;

class ErrorMultipleBindsBindDataSetup {

    @Setup
    @BindData(id = 0, view = View.class, set="text")
    void bind() {
    }
}
