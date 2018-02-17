package se.snylt.witchprocessortest;

import android.widget.CompoundButton;

class CompoundButtonViewModel extends TestViewModel {

    final Boolean checked = true;

    final Integer buttonDrawable = 66;

    CompoundButtonViewModel() {
        super(CompoundButton.class);
    }
}
