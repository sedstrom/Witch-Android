package se.snylt.zipper.viewbinder.onbind;

import android.view.View;

public class PropertyOnBind implements OnBind<View> {

    private final String property;

    public PropertyOnBind(String property) {
        this.property = property;
    }

    @Override
    public void onBind(View view, Object value) {
        PropertyMethodUtils.setProperty(view, property, value);
    }
}
