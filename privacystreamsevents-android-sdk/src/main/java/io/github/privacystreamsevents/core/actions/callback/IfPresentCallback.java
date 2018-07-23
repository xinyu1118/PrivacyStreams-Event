package io.github.privacystreamsevents.core.actions.callback;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamAction;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Callback once an item is present in the stream.
 */

class IfPresentCallback extends PStreamAction {
    private final Function<Item, Void> itemCallback;

    IfPresentCallback(Function<Item, Void> itemCallback) {
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
