package se.snylt.zipper.viewbinder.onbind;

import android.widget.CompoundButton;

import static se.snylt.zipper.viewbinder.utils.InstanceOf.bool;

public class CompoundButtonOnBind implements OnBind<CompoundButton> {

    @Override
    public void onBind(CompoundButton view, Object value) {
        if(bool(value)) {
            view.setChecked((Boolean) value);
        }
    }

    public static boolean canBindValue(Object value) {
        return bool(value);
    }
}
