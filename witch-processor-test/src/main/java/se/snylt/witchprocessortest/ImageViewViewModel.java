package se.snylt.witchprocessortest;

import android.widget.ImageView;

class ImageViewViewModel extends TestViewModel {

    final Integer imageId = 666;

    String contentDescription = "a description";

    ImageViewViewModel() {
        super(ImageView.class);
    }
}
