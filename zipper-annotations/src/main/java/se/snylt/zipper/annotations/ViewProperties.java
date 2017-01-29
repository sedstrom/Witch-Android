package se.snylt.zipper.annotations;

public class ViewProperties {

    public final static String DEFAULT = "&&&";

    public final class TextView {
        public final static String TEXT = "text";
    }

    public final class EditText {
        public final static String TEXT = TextView.TEXT;
        public final static String HINT = "hint";
    }

    public final class ImageView {
        public final static String IMG_RESOURCE = "imageResource";
        public final static String IMG_BITMAP = "imageBitmap";
        public final static String IMG_DRAWABLE = "imageDrawable";
    }

    public final class CompoundButton {
        public final static String CHECKED = "checked";
    }


}
