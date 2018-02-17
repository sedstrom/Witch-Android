package com.example.witch.alwaysbind;

import com.example.witch.R;

import android.widget.SeekBar;

import se.snylt.witch.annotations.BindData;

class NoAlwaysBindViewModel {

    @BindData(id = R.id.always_bind_fragment_seek_bar, view = SeekBar.class, set = "progress")
    final Integer progress;

    NoAlwaysBindViewModel(int progress) {
        this.progress = progress;
    }
}
