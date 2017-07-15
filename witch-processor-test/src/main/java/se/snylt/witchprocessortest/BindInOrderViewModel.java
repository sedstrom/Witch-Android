package se.snylt.witchprocessortest;

import android.widget.EditText;

import se.snylt.witch.annotations.BindToEditText;
import se.snylt.witch.annotations.BindToTextView;

class BindInOrderViewModel extends TestViewModel {

    @BindToTextView(id = 1)
    final String first = "first";

    @BindToEditText(id = 2)
    final String second = "second";


    @BindToTextView(id = 3)
    final String third = "third";

    BindInOrderViewModel() {
        super(EditText.class);
    }
}
