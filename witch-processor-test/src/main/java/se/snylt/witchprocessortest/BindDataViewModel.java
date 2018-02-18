package se.snylt.witchprocessortest;

import android.view.View;
import android.widget.TextView;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.viewbinder.Value;

class BindDataViewModel {

    @BindData(id = R.id.testIdOne, view = TextView.class, set = "text")
    String foo = "foo";

    @Data
    String bar() { return "bar"; }

    @Bind(id = R.id.testIdTwo)
    void bar(TextView view, String bar) {
        view.setText(bar);
    }

    @Data
    int abra() { return 1; }

    @Bind(id = R.id.testIdThree)
    void abra(TextView view, int doe) {
        view.setText(doe);
    }


}
