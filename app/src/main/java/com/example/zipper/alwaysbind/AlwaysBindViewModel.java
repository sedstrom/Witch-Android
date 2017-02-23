package com.example.zipper.alwaysbind;

import com.example.zipper.R;

import android.widget.SeekBar;

import se.snylt.zipper.annotations.AlwaysBind;
import se.snylt.zipper.annotations.BindToView;

public class AlwaysBindViewModel {

    @AlwaysBind // Ignores binding history
    @BindToView(id = R.id.always_bind_fragment_seek_bar, view = SeekBar.class, set = "progress")
    public final Integer progress;

    public AlwaysBindViewModel(int progress) {
        this.progress = progress;
    }
}
