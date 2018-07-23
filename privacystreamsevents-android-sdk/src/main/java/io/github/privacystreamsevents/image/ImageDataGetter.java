package io.github.privacystreamsevents.image;


import io.github.privacystreamsevents.core.UQI;

/**
 * Get image data from a stream of items.
 */
class ImageDataGetter extends ImageProcessor<ImageData> {

    ImageDataGetter(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected ImageData processImage(UQI uqi, ImageData imageData) {
        if (imageData == null) return null;
        return imageData;
    }
}
