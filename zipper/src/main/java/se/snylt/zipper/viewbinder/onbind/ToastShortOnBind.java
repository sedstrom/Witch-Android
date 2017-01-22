package se.snylt.zipper.viewbinder.onbind;

import android.widget.Toast;

public class ToastShortOnBind extends ToastOnBind {

    @Override
    protected int getToastLength() {
        return Toast.LENGTH_SHORT;
    }
}
