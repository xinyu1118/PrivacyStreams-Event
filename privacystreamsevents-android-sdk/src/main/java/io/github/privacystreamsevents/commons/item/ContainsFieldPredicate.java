package io.github.privacystreamsevents.commons.item;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Check if the item contains a certain field
 */

class ContainsFieldPredicate extends ItemOperator<Boolean> {
    private final String fieldToCheck;

    ContainsFieldPredicate(String fieldToCheck) {
        this.fieldToCheck = Assertions.notNull("fieldToCheck", fieldToCheck);
        this.addParameters(fieldToCheck);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        return input.containsField(fieldToCheck);
    }
}
