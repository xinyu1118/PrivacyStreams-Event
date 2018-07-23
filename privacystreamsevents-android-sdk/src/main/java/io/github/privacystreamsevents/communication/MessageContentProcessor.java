package io.github.privacystreamsevents.communication;


import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Process the content field in a Message item.
 * @param <Tout> the content type
 */
abstract class MessageContentProcessor<Tout> extends ItemOperator<Tout> {
    private final String contentField;

    MessageContentProcessor(String contentField) {
        this.contentField = Assertions.notNull("contentField", contentField);
        this.addParameters(contentField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        String content = input.getValueByField(this.contentField);
        return this.processMessage(uqi, content);
    }

    protected abstract Tout processMessage(UQI uqi, String content);
}
