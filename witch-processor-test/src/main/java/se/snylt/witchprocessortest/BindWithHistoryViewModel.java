package se.snylt.witchprocessortest;

import android.widget.TextView;

import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import se.snylt.witch.annotations.Data;

class BindWithHistoryViewModel {

    @Data
    String data = null;

    @Bind(id = R.id.testIdOne)
    void data(TextView view, String data, String history) {
        view.setText(data);
    }


}
