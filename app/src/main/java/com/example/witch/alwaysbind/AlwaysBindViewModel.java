package com.example.witch.alwaysbind;

import com.example.witch.R;

import android.widget.SeekBar;

import se.snylt.witch.annotations.AlwaysBind;
import se.snylt.witch.annotations.BindToView;

class AlwaysBindViewModel {

    @AlwaysBind // Ignores binding history
    @BindToView(id = R.id.always_bind_fragment_seek_bar, view = SeekBar.class, set = "progress")
    final Integer progress;

    AlwaysBindViewModel(int progress) {
        this.progress = progress;
    }
}
