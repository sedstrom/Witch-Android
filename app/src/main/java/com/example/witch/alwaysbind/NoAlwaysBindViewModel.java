package com.example.witch.alwaysbind;

import com.example.witch.R;

import android.widget.SeekBar;

import se.snylt.witch.annotations.BindToView;

public class NoAlwaysBindViewModel {

    @BindToView(id = R.id.always_bind_fragment_seek_bar, view = SeekBar.class, set = "progress")
    public final Integer progress;

    public NoAlwaysBindViewModel(int progress) {
        this.progress = progress;
    }
}
