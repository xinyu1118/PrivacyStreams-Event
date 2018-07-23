package io.github.privacystreamsevents.core.actions.callback;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamAction;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Callback with each item in the stream.
 * The callback will be invoked with the item map as a parameter.
 */
class ForEachCallback extends PStreamAction {
    private final Function<Item, Void> itemCallback;

    ForEachCallback(Function<Item, Void> itemCallback) {
        this.itemCallback = Assertions.notNull("itemCallback", itemCallback);
        this.addParameters(itemCallback);
    }

    @Override
    protected void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        this.itemCallback.apply(this.getUQI(), item);
    }
}
