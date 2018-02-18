package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.BindNull;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.viewbinder.Value;

class BindNullViewModel {

    @BindData(id = R.id.testIdOne, view = TextView.class, set = "text")
    @BindNull
    String one = null;

    @Data
    @BindNull
    String two = null;

    @Bind(id = R.id.testIdTwo)
    void two(TextView textView, String nullString) {
        textView.setText(nullString);
    }

    @Data
    String three = null;

    @BindNull
    @Bind(id = R.id.testIdThree)
    void three(TextView textView, String nullString) {
        textView.setText(nullString);
    }

    @Data
    String four = null;

    @Bind(id = R.id.testIdFour)
    void four(TextView textView, String notNullString) {
        textView.setText(notNullString);
    }

    @BindData(id = R.id.testIdFive, view = TextView.class, set = "text")
    String five = null;

}
