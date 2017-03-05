package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.BindToTextView;

public class TextViewViewModel extends TestViewModel {

    @BindToTextView(id = android.R.id.button1)
    public final String text = "text";

    @BindToTextView(id = android.R.id.button2, set = "contentDescription")
    public String contentDescription = "a content description";

    public TextViewViewModel() {
        super(TextView.class);
    }
}
