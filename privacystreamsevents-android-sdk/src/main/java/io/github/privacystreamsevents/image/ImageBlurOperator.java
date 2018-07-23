package io.github.privacystreamsevents.image;


import android.os.Build;
import android.support.annotation.RequiresApi;

import io.github.privacystreamsevents.core.UQI;

/**
 * Blur an image and return the blurred image data.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
class ImageBlurOperator extends ImageProcessor<ImageData> {

    ImageBlurOperator(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected ImageData processImage(UQI uqi, ImageData imageData) {
        return imageData.getBlurred(uqi);
    }

}
