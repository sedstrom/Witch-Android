package se.snylt.witchprocessortest;

import android.widget.ImageView;

import se.snylt.witch.annotations.BindToImageView;

public class ImageViewViewModel extends TestViewModel {

    @BindToImageView(id = android.R.id.button1)
    public final Integer imageId = 666;

    @BindToImageView(id = android.R.id.button2, set = "contentDescription")
    public String contentDescription = "a description";

    public ImageViewViewModel() {
        super(ImageView.class);
    }
}
