package io.github.privacystreamsevents.location;


import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;
import io.github.privacystreamsevents.utils.LocationUtils;

/**
 * Calculate the distance between current location and the destination in meters.
 */
public class LocationDestinationCalculator extends ItemOperator<Double> {
    private final String latLonField;
    private final LatLon destination;

    LocationDestinationCalculator(String latLonField, LatLon destination) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.destination = Assertions.notNull("destination", destination);
        this.addParameters(latLonField, destination);
    }

    @Override
    public Double apply(UQI uqi, Item input) {
        LatLon latLon = input.getValueByField(this.latLonField);
        return LocationUtils.getDistanceBetween(latLon, destination);
    }
}