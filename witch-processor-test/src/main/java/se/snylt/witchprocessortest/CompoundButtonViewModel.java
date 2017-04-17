package se.snylt.witchprocessortest;

import android.widget.CompoundButton;

import se.snylt.witch.annotations.BindToCompoundButton;

class CompoundButtonViewModel extends TestViewModel {

    @BindToCompoundButton(id = android.R.id.button1)
    final Boolean checked = true;

    @BindToCompoundButton(id = android.R.id.button2, set = "buttonDrawable")
    final Integer buttonDrawable = 66;

    CompoundButtonViewModel() {
        super(CompoundButton.class);
    }
}
