package io.github.privacystreamsevents.sensor;

import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamProvider;
import io.github.privacystreamsevents.utils.annotations.PSItem;
import io.github.privacystreamsevents.utils.annotations.PSItemField;

/**
 * Ambient relative humidity sensor.
 */
@PSItem
public class RelativeHumidity extends Item {

    /**
     * Ambient relative humidity. Unit: %.
     */
    @PSItemField(type = Float.class)
    public static final String HUMIDITY = "humidity";

    RelativeHumidity(float humidity) {
        this.setFieldValue(HUMIDITY, humidity);
    }

    /**
     * Provide a live stream of sensor readings from ambient relative humidity sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new RelativeHumidityUpdatesProvider(sensorDelay);
    }
}
