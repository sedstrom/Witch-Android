package se.snylt.witchprocessortest;

import android.widget.EditText;

class EditTextViewModel extends TestViewModel {

    final String text = "text";

    String hint = "a hint text";

    EditTextViewModel() {
        super(EditText.class);
    }
}
