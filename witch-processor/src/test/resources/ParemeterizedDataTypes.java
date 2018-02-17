package com.example.witch.app;

import java.util.ArrayList;
import java.util.List;
import se.snylt.witch.annotations.Data;
import se.snylt.witch.annotations.Bind;
import se.snylt.witch.annotations.BindData;
import android.view.View;

class ParemeterizedDataTypes {

    @BindData(id = 0, view = View.class, set = "tag")
    List<String> something = new ArrayList<>();

    @Data
    List<Integer> numbers = new ArrayList<>();

    @Bind(id = 0)
    void numbers(View view, List<Integer> numbers) {

    }

    @Data
    List<String> strings() {
        return new ArrayList<>();
    }

    @Bind(id = 0)
    void strings(View view, List<String> strings) {

    }
}
