package io.github.privacystreamsevents.core.transformations.map;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStreamTransformation;

import static io.github.privacystreamsevents.utils.Assertions.notNull;

/**
 * Transform a stream to another stream by mapping each item in the stream with a item mapper function.
 */
final class PerItemMapper extends PStreamTransformation {

    private final Function<Item, Item> itemMapper;

    PerItemMapper(final Function<Item, Item> itemMapper) {
        this.itemMapper = notNull("itemMapper", itemMapper);
        this.addParameters(itemMapper);
    }

    @Override
    protected final void onInput(Item item) {
        if (item.isEndOfStream()) {
            this.finish();
            return;
        }
        Item outputItem = this.itemMapper.apply(this.getUQI(), item);
        this.output(outputItem);
    }
}
