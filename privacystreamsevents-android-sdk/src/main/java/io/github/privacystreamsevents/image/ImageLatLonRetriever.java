package io.github.privacystreamsevents.image;


import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.location.LatLon;

/**
 * Retrieve the latitude and longitude of the image.
 */
class ImageLatLonRetriever extends ImageProcessor<LatLon> {

    ImageLatLonRetriever(String imageDataField) {
        super(imageDataField);
    }

    @Override
    protected LatLon processImage(UQI uqi, ImageData imageData) {
        return imageData.getLatLon(uqi);
    }

}
