package se.snylt.zipper.viewbinder.onbind;

import android.widget.Toast;

public class ToastLongOnBind extends ToastOnBind {

    @Override
    protected int getToastLength() {
        return Toast.LENGTH_LONG;
    }
}
