package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindData;

class PrimitiveBindViewModel extends TestViewModel {

    /*
    @BindData(id = 1, view = View.class, set = "tag")
    final int primtiveInt = 1;

    @BindData(id = 2, view = View.class, set = "tag")
    final float primitiveFloat = 1f;

    @BindData(id = 3, view = View.class, set = "tag")
    final double primitiveDouble = 1d;

    @BindData(id = 4, view = View.class, set = "tag")
    final byte primitiveByte = 1;

    @BindData(id = 5, view = View.class, set = "tag")
    final long primitiveLong = 1l;

    @BindData(id = 6, view = View.class, set = "tag")
    final short primitiveShort = 1;

    @BindData(id = 7, view = View.class, set = "tag")
    final char primitiveChar = 1;

    @BindData(id = 8, view = View.class, set = "tag")
    final boolean primitiveBoolean = true;

    */

    PrimitiveBindViewModel() {
        super(View.class);
    }
}