package io.github.privacystreamsevents.commons.item;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;

import static io.github.privacystreamsevents.utils.Assertions.notNull;

/**
 * Project the fields in an item.
 * Either include some fields or exclude some fields.
 */
class ItemProjector extends ItemOperator<Item> {
    final static String OPERATOR_INCLUDE = "$include_fields";
    final static String OPERATOR_EXCLUDE = "$exclude_fields";

    private final String operator;
    private final String[] fields;

    ItemProjector(String operator, String... fields) {
        this.operator = notNull("operator", operator);
        this.fields = notNull("fields", fields);
        this.addParameters(operator, fields);
    }

    @Override
    public Item apply(UQI uqi, Item input) {
        switch (this.operator) {
            case OPERATOR_INCLUDE:
                input.includeFields(this.fields);
                return input;
            case OPERATOR_EXCLUDE:
                input.excludeFields(this.fields);
                return input;
            default:
                throw new IllegalArgumentException("illegal operator: " + this.operator);
        }
    }

}
