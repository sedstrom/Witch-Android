package se.snylt.zipperprocessortest;

import android.widget.EditText;

import se.snylt.zipper.annotations.BindToEditText;

public class EditTextViewModel extends TestViewModel {

    @BindToEditText(id = 1)
    public final String text = "text";

    @BindToEditText(id = 2, set = "hint")
    public String hint = "a hint text";

    public EditTextViewModel() {
        super(EditText.class);
    }
}
