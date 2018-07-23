package io.github.privacystreamsevents.core.transformations.reorder;

import io.github.privacystreamsevents.core.Item;

import java.util.Collections;
import java.util.List;

/**
 * Reverse the order of items in the stream.
 */
class StreamReverser extends StreamReorder {
    @Override
    protected void reorder(List<Item> items) {
        Collections.reverse(items);
    }
}
