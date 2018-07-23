package io.github.privacystreamsevents.communication;


import io.github.privacystreamsevents.core.UQI;

/**
 * Get message content.
 */
class MessageContentGetter extends MessageContentProcessor<String> {

    MessageContentGetter(String contentField) {
        super(contentField);
    }

    @Override
    protected String processMessage(UQI uqi, String content) {
        if (content == null) return null;
        return content;
    }
}
