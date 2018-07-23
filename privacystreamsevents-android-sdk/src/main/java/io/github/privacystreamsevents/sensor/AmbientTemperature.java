package io.github.privacystreamsevents.sensor;

import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamProvider;
import io.github.privacystreamsevents.utils.annotations.PSItem;
import io.github.privacystreamsevents.utils.annotations.PSItemField;

/**
 * Ambient air temperature sensor.
 */
@PSItem
public class AmbientTemperature extends Item {

    /**
     * Ambient air temperature. Unit: °C.
     */
    @PSItemField(type = Float.class)
    public static final String TEMPERATURE = "temperature";

    AmbientTemperature(float temperature) {
        this.setFieldValue(TEMPERATURE, temperature);
    }

    /**
     * Provide a live stream of sensor readings from air temperature sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new AmbientTemperatureUpdatesProvider(sensorDelay);
    }
}
