package se.snylt.zipper.viewbinder.onbind;

import android.view.View;
import android.widget.Toast;

import static se.snylt.zipper.viewbinder.utils.InstanceOf.charSequence;
import static se.snylt.zipper.viewbinder.utils.InstanceOf.integer;
import static se.snylt.zipper.viewbinder.utils.InstanceOf.string;

public abstract class ToastOnBind implements OnBind<View> {

    public static boolean canBindValue(Object value) {
        return integer(value) || charSequence(value);
    }

    @Override
    public void onBind(View view, Object value) {
        if(integer(value)) {
            Toast.makeText(view.getContext(), (int)value, getToastLength()).show();
        } else if(string(value)) {
            Toast.makeText(view.getContext(), (String)value, getToastLength()).show();
        }
    }

    protected abstract int getToastLength();
}
