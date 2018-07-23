package io.github.privacystreamsevents.location;


import android.content.Context;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Process the location postcode field in an item.
 * @param <Tout> the postcode type
 */
abstract class PostcodeProcessor<Tout> extends ItemOperator<Tout> {
    private final String latLonField;

    PostcodeProcessor(String latLonField) {
        this.latLonField = Assertions.notNull("latLonField", latLonField);
        this.addParameters(this.latLonField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        LatLon latLon = input.getValueByField(this.latLonField);
        Context context = uqi.getContext();
        return this.processPostcode(context, latLon);
    }

    protected abstract Tout processPostcode(Context context, LatLon latLon);
}