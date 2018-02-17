package com.example.witch.undo;

import com.example.witch.R;

import android.view.View;

import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindWhen;

public class UndoViewModel {

    @BindData(id = R.id.undo_fragment_undo_button, view = View.class, set = "enabled")
    public final Boolean undoEnabled;

    @BindWhen(BindWhen.NOT_EQUALS)
    public final String text;

    public final Integer selection;

    public UndoViewModel(String text, boolean undoEnabled) {
        this.text = text;
        this.selection = text.length();
        this.undoEnabled = undoEnabled;
    }

    public UndoViewModel(String text) {
        this(text, true);
    }
}
