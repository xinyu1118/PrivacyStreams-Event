package io.github.privacystreamsevents.commons.logic;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Compute NOT result of a boolean function.
 */
class NotOperator extends LogicOperator {

    private Function<Item, Boolean> function;

    NotOperator(Function<Item, Boolean> function) {
        this.function = Assertions.notNull("function", function);
        this.addParameters(function);
    }

    @Override
    public Boolean apply(UQI uqi, Item input) {
        boolean result = this.function.apply(uqi, input);
        return !result;
    }
}
