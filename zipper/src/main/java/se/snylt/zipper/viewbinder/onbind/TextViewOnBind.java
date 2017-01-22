package se.snylt.zipper.viewbinder.onbind;

import android.widget.TextView;

import static se.snylt.zipper.viewbinder.utils.InstanceOf.charSequence;
import static se.snylt.zipper.viewbinder.utils.InstanceOf.integer;

public class TextViewOnBind implements OnBind<TextView> {

    public static boolean canBindValue(Object value) {
        return charSequence(value) || integer(value);
    }

    @Override
    public void onBind(TextView view, Object value) {
        if(charSequence(value)) {
            view.setText((CharSequence) value);
        } else if(integer(value)) {
            view.setText((Integer)value);
        }
    }
}
