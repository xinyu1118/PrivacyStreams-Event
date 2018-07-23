package io.github.privacystreamsevents.accessibility;

import io.github.privacystreamsevents.commons.item.ItemOperators;
import io.github.privacystreamsevents.core.Callback;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamProvider;
import io.github.privacystreamsevents.core.purposes.Purpose;
import io.github.privacystreamsevents.utils.AppUtils;

/**
 * Provide a live stream of browser search events.
 */

class BrowserSearchEventsProvider extends PStreamProvider {

    @Override
    protected void provide() {
        getUQI().getData(AccEvent.asTextEntries(), Purpose.LIB_INTERNAL("Event Triggers"))
                .filter(ItemOperators.isFieldIn(AccEvent.PACKAGE_NAME,
                        new String[]{
                                AppUtils.APP_PACKAGE_SEARCHBOX,
                                AppUtils.APP_PACKAGE_FIREFOX,
                                AppUtils.APP_PACKAGE_OPERA,
                                AppUtils.APP_PACKAGE_CHROME}))
                .forEach(new Callback<Item>() {
                    @Override
                    protected void onInput(Item input) {
                        output(new BrowserSearch(input.getValueByField(AccEvent.TEXT).toString()));
                    }
                });
    };
}
