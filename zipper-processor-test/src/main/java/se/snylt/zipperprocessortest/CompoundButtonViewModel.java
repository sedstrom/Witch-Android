package se.snylt.zipperprocessortest;

import android.widget.CompoundButton;

import se.snylt.zipper.annotations.BindToCompoundButton;

public class CompoundButtonViewModel extends TestViewModel {

    @BindToCompoundButton(id = 1)
    public final Boolean checked = true;

    @BindToCompoundButton(id = 1, set = "buttonDrawable")
    public final Integer buttonDrawable = 66;

    public CompoundButtonViewModel() {
        super(CompoundButton.class);
    }
}
