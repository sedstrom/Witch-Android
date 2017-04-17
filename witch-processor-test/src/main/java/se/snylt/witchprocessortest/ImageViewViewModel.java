package se.snylt.witchprocessortest;

import android.widget.ImageView;

import se.snylt.witch.annotations.BindToImageView;

class ImageViewViewModel extends TestViewModel {

    @BindToImageView(id = android.R.id.button1)
    final Integer imageId = 666;

    @BindToImageView(id = android.R.id.button2, set = "contentDescription")
    String contentDescription = "a description";

    ImageViewViewModel() {
        super(ImageView.class);
    }
}
