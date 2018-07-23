package io.github.privacystreamsevents.commons.debug;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Logging;

/**
 * Print the item for debugging
 */

final class DebugPrintOperator<Tin> extends Function<Tin, Void> {
    @Override
    public Void apply(UQI uqi, Tin input) {
        String debugMsg;

        if (input instanceof Item) debugMsg = ((Item) input).toDebugString();
        else debugMsg = "" + input;

        Logging.debug(debugMsg);
        return null;
    }

}
