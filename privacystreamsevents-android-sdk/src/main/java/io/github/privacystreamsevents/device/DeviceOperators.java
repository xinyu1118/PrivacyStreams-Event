package io.github.privacystreamsevents.device;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

/**
 * A wrapper class of device-related functions.
 */
@PSOperatorWrapper
public class DeviceOperators {
    /**
     * Get device id.
     *
     * @return the function.
     */
    public static Function<Void, String> getDeviceId() {
        return new DeviceIdGetter();
    }

    /**
     * Check if wifi is connected.
     *
     * @return the function.
     */
    // @RequiresPermission(value = Manifest.permission.ACCESS_WIFI_STATE)
    public static Function<Void, Boolean> isWifiConnected() {
        return new WifiStatusChecker();
    }
}
