package se.snylt.witchprocessortest;

import android.view.View;

import se.snylt.witch.annotations.BindToView;

class PrimitiveBindViewModel extends TestViewModel {

    @BindToView(id = 1, view = View.class, set = "tag")
    final int primtiveInt = 1;

    @BindToView(id = 2, view = View.class, set = "tag")
    final float primitiveFloat = 1f;

    @BindToView(id = 3, view = View.class, set = "tag")
    final double primitiveDouble = 1d;

    @BindToView(id = 4, view = View.class, set = "tag")
    final byte primitiveByte = 1;

    @BindToView(id = 5, view = View.class, set = "tag")
    final long primitiveLong = 1l;

    @BindToView(id = 6, view = View.class, set = "tag")
    final short primitiveShort = 1;

    @BindToView(id = 7, view = View.class, set = "tag")
    final char primitiveChar = 1;

    @BindToView(id = 8, view = View.class, set = "tag")
    final boolean primitiveBoolean = true;

    PrimitiveBindViewModel() {
        super(View.class);
    }
}