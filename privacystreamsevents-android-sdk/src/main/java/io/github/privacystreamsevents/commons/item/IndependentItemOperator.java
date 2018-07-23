package io.github.privacystreamsevents.commons.item;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * A function that is independent from current item.
 */

class IndependentItemOperator<Tout> extends ItemOperator<Tout> {
    private Function<Void, Tout> voidInFunction;

    IndependentItemOperator(Function<Void, Tout> voidInFunction) {
        this.voidInFunction = Assertions.notNull("voidInFunction", voidInFunction);
        this.addParameters(voidInFunction);
    }

    @Override
    public Tout apply(UQI uqi, Item input) {
        return this.voidInFunction.apply(uqi, null);
    }
}
