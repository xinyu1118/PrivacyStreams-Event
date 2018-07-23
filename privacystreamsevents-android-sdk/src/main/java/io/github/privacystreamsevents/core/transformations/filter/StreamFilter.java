package io.github.privacystreamsevents.core.transformations.filter;

import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamTransformation;

/**
 * Exclude some items from PStream
 */

abstract class StreamFilter extends PStreamTransformation {

    @Override
    protected void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        if (this.keep(item)) this.output(item);
    }

    protected abstract boolean keep(Item item);
}
