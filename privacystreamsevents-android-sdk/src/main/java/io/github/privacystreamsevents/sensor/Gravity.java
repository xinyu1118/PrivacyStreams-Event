package io.github.privacystreamsevents.sensor;

import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamProvider;
import io.github.privacystreamsevents.utils.annotations.PSItem;
import io.github.privacystreamsevents.utils.annotations.PSItemField;

/**
 * Gravity sensor.
 */
@PSItem
public class Gravity extends Item {

    /**
     * Force of gravity along the x axis.
     */
    @PSItemField(type = Float.class)
    public static final String X = "x";

    /**
     * Force of gravity along the y axis.
     */
    @PSItemField(type = Float.class)
    public static final String Y = "y";

    /**
     * Force of gravity along the z axis.
     */
    @PSItemField(type = Float.class)
    public static final String Z = "z";

    Gravity(float x, float y, float z) {
        this.setFieldValue(X, x);
        this.setFieldValue(Y, y);
        this.setFieldValue(Z, z);
    }

    /**
     * Provide a live stream of sensor readings from gravity sensor.
     * @return the provider.
     */
    public static PStreamProvider asUpdates(int sensorDelay){
        return new GravityUpdatesProvider(sensorDelay);
    }
}
