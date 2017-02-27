package se.snylt.zipperprocessortest;

import android.widget.ImageView;

import se.snylt.zipper.annotations.BindToImageView;

public class ImageViewViewModel extends TestViewModel {

    @BindToImageView(id = 1)
    public final Integer imageId = 666;

    @BindToImageView(id = 2, set = "contentDescription")
    public String contentDescription = "a description";

    public ImageViewViewModel() {
        super(ImageView.class);
    }
}
