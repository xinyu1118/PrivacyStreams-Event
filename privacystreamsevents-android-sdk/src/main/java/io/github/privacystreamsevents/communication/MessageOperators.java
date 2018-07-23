package io.github.privacystreamsevents.communication;


import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access message-related operators.
 */
@PSOperatorWrapper
public class MessageOperators {

    /**
     * Get the phone number from incoming messages.
     *
     * @return the function
     */
    public static Function<Item, String> getMessagePhones() {
        String contactField = Message.CONTACT;
        return new MessagePhoneGetter(contactField);
    }

    /**
     * Get incoming message content.
     *
     * @return the function
     */
    public static Function<Item, String> getMessageContent() {
        String contentField = Message.CONTENT;
        return new MessageContentGetter(contentField);
    }

}
