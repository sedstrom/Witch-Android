package se.snylt.zipper.viewbinder.onbind;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import static se.snylt.zipper.viewbinder.utils.InstanceOf.bitmap;
import static se.snylt.zipper.viewbinder.utils.InstanceOf.drawable;
import static se.snylt.zipper.viewbinder.utils.InstanceOf.integer;

public class ImageViewOnBind implements OnBind<ImageView> {

    public static boolean canBindValue(Object value) {
        return integer(value) || drawable(value) || bitmap(value);
    }

    @Override
    public void onBind(ImageView view, Object value) {
        if(integer(value)) {
            view.setImageResource((Integer) value);
        } else if (drawable(value)) {
            view.setImageDrawable((Drawable) value);
        } else if (bitmap(value)) {
            view.setImageBitmap((Bitmap) value);
        }
    }
}
