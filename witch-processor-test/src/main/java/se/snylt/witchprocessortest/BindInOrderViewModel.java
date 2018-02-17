package se.snylt.witchprocessortest;

import android.widget.EditText;

class BindInOrderViewModel extends TestViewModel {

    final String first = "first";

    final String second = "second";

    final String third = "third";

    BindInOrderViewModel() {
        super(EditText.class);
    }
}
