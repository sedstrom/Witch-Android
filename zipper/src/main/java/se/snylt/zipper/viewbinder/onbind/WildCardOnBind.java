package se.snylt.zipper.viewbinder.onbind;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

public class WildCardOnBind {

    public static OnBind findOnBind(View view, Object value) {
        // TextViewOnBind
        if(view instanceof TextView && TextViewOnBind.canBindValue(value)) {
            return new TextViewOnBind();
        }

        // ImageViewOnBind
        if(view instanceof ImageView && ImageViewOnBind.canBindValue(value)) {
            return new ImageViewOnBind();
        }

        // CompoundButtonOnBind
        if(view instanceof CompoundButton && CompoundButtonOnBind.canBindValue(value)) {
            return new CompoundButtonOnBind();
        }

        // RadioGroupOnBind
        if(view instanceof RadioGroup && RadioGroupOnBind.canBindValue(value)) {
            return new RadioGroupOnBind();
        }

        return null;
    }
}
