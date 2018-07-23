package io.github.privacystreamsevents.image;


import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.location.LatLon;

/**
 * Detect faces in an image.
 */
class ImageFaceDetector extends ImageProcessor<Boolean> {

    ImageFaceDetector(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected Boolean processImage(UQI uqi, ImageData imageData) {
        return imageData.hasFace(uqi);
    }

}
