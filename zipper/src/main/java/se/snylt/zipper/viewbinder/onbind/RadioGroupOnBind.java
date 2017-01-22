package se.snylt.zipper.viewbinder.onbind;

import android.widget.RadioButton;
import android.widget.RadioGroup;

import static se.snylt.zipper.viewbinder.utils.InstanceOf.integer;

public class RadioGroupOnBind implements OnBind<RadioGroup> {

    @Override
    public void onBind(RadioGroup view, Object value) {
        if(integer(value)) {
            RadioButton b = (RadioButton) view.getChildAt((Integer) value);
            b.setChecked(true);
        }
    }

    public static boolean canBindValue(Object value) {
        return integer(value);
    }
}
