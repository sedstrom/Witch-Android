package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.BindToTextView;

class TextViewViewModel extends TestViewModel {

    @BindToTextView(id = android.R.id.button1)
    final String text = "text";

    @BindToTextView(id = android.R.id.button2, set = "contentDescription")
    String contentDescription = "a content description";

    TextViewViewModel() {
        super(TextView.class);
    }
}
