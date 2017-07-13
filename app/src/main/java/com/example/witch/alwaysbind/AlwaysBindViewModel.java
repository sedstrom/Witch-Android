package com.example.witch.alwaysbind;

import com.example.witch.R;

import android.widget.SeekBar;

import se.snylt.witch.annotations.BindToView;
import se.snylt.witch.annotations.BindWhen;

class AlwaysBindViewModel {

    @BindWhen(BindWhen.ALWAYS) // Ignores binding history
    @BindToView(id = R.id.always_bind_fragment_seek_bar, view = SeekBar.class, set = "progress")
    final int progress;

    AlwaysBindViewModel(int progress) {
        this.progress = progress;
    }
}
