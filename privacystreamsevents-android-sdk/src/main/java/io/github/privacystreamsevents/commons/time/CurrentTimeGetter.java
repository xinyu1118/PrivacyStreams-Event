package io.github.privacystreamsevents.commons.time;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.UQI;

/**
 * Generate a time tag string
 */
final class CurrentTimeGetter extends Function<Void, Long> {
    CurrentTimeGetter() {
    }

    @Override
    public Long apply(UQI uqi, Void input) {
        return System.currentTimeMillis();
    }
}
