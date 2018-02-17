package se.snylt.witchprocessortest;

import android.widget.TextView;

class TextViewViewModel extends TestViewModel {

    final String text = "text";

    String contentDescription = "a content description";

    TextViewViewModel() {
        super(TextView.class);
    }
}
