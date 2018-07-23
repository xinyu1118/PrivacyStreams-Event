package io.github.privacystreamsevents.location;


import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Get the precise location.
 */
public class LocationCoordinateGetter extends ItemOperator<LatLon>{
    private final String latLonField;

    LocationCoordinateGetter(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(latLonField);
    }

    @Override
    public LatLon apply(UQI uqi, Item input) {
        return input.getValueByField(this.latLonField);
    }
}
