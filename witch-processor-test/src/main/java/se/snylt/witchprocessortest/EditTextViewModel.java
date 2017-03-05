package se.snylt.witchprocessortest;

import android.widget.EditText;

import se.snylt.witch.annotations.BindToEditText;

public class EditTextViewModel extends TestViewModel {

    @BindToEditText(id = android.R.id.button1)
    public final String text = "text";

    @BindToEditText(id = android.R.id.button2, set = "hint")
    public String hint = "a hint text";

    public EditTextViewModel() {
        super(EditText.class);
    }
}
