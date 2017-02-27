package se.snylt.zipperprocessortest;

import android.widget.TextView;

import se.snylt.zipper.annotations.BindToTextView;

public class TextViewViewModel extends TestViewModel {

    @BindToTextView(id = 1)
    public final String text = "text";

    @BindToTextView(id = 2, set = "contentDescription")
    public String contentDescription = "a content description";

    public TextViewViewModel() {
        super(TextView.class);
    }
}
