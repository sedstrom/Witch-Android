package com.example.witch.undo;

import com.example.witch.R;

import android.view.View;

import se.snylt.witch.annotations.BindToEditText;
import se.snylt.witch.annotations.BindToView;

public class UndoViewModel {


    @BindToView(id = R.id.undo_fragment_undo_button, view = View.class, set = "enabled")
    public final Boolean undoEnabled;

    @BindToEditText(id = R.id.undo_fragment_add_edittext)
    public final String text;

    @BindToEditText(id = R.id.undo_fragment_add_edittext, set = "selection")
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
