package io.github.privacystreamsevents.core.transformations.reorder;

import io.github.privacystreamsevents.core.Item;

import java.util.Collections;
import java.util.List;

/**
 * Shuffle the items in stream.
 */
final class StreamShuffler extends StreamReorder {
    StreamShuffler() {
    }

    @Override
    protected void reorder(List<Item> items) {
        Collections.shuffle(items);
    }
}
