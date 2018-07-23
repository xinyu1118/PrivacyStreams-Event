package io.github.privacystreamsevents.communication;

import java.util.List;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Process the phone field in a Contact item.
 * @param <Tout> the phone type
 */
abstract class ContactPhoneProcessor<Tout> extends ItemOperator<Tout> {
    private final String phonesField;

    ContactPhoneProcessor(String phonesField) {
        this.phonesField = Assertions.notNull("phonesField", phonesField);
        this.addParameters(phonesField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        List<String> phones = input.getValueByField(this.phonesField);
        return this.processCall(uqi, phones);
    }

    protected abstract Tout processCall(UQI uqi, List<String> phones);
}
