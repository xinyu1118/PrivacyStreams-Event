package io.github.privacystreamsevents.device;

import android.Manifest;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.DeviceUtils;

/**
 * Get device id
 */
class WifiStatusChecker extends Function<Void, Boolean> {

    WifiStatusChecker() {
        this.addRequiredPermissions(Manifest.permission.ACCESS_WIFI_STATE);
    }

    @Override
    public Boolean apply(UQI uqi, Void input) {
        return DeviceUtils.isWifiConnected(uqi.getContext());
    }
}
