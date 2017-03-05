package se.snylt.witchprocessortest;

import android.widget.CompoundButton;

import se.snylt.witch.annotations.BindToCompoundButton;

public class CompoundButtonViewModel extends TestViewModel {

    @BindToCompoundButton(id = android.R.id.button1)
    public final Boolean checked = true;

    @BindToCompoundButton(id = android.R.id.button2, set = "buttonDrawable")
    public final Integer buttonDrawable = 66;

    public CompoundButtonViewModel() {
        super(CompoundButton.class);
    }
}
