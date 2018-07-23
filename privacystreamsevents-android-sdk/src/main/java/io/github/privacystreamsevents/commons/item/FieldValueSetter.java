package io.github.privacystreamsevents.commons.item;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;

import static io.github.privacystreamsevents.utils.Assertions.notNull;

/**
 * Set a value to a field in the item.
 */

class FieldValueSetter<TValue> extends ItemOperator<Item> {

    private final String fieldToSet;
    private final TValue fieldValue;

    FieldValueSetter(String fieldToSet, TValue fieldValue) {
        this.fieldToSet = notNull("fieldToSet", fieldToSet);
        this.fieldValue = notNull("fieldValue", fieldValue);
        this.addParameters(fieldToSet, fieldValue);
    }

    @Override
    public Item apply(UQI uqi, Item input) {
        input.setFieldValue(this.fieldToSet, this.fieldValue);
        return input;
    }

}
