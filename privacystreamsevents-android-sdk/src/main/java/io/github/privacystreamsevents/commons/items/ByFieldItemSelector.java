package io.github.privacystreamsevents.commons.items;

import io.github.privacystreamsevents.commons.ItemsOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

import java.util.List;


/**
 * select an item from the items
 * return null if fails to find an item
 */

abstract class ByFieldItemSelector extends ItemsOperator<Item> {
    protected final String field;

    ByFieldItemSelector(String field) {
        this.field = Assertions.notNull("field", field);
        this.addParameters(field);
    }

    protected abstract Item selectFrom(List<Item> items);

    @Override
    public Item apply(UQI uqi, List<Item> input) {
        return this.selectFrom(input);
    }
}
