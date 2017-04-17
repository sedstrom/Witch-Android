package se.snylt.witchprocessortest;

import android.widget.EditText;

import se.snylt.witch.annotations.BindToEditText;

class EditTextViewModel extends TestViewModel {

    @BindToEditText(id = android.R.id.button1)
    final String text = "text";

    @BindToEditText(id = android.R.id.button2, set = "hint")
    String hint = "a hint text";

    EditTextViewModel() {
        super(EditText.class);
    }
}
