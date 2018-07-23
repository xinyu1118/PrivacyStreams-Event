package io.github.privacystreamsevents.communication;


import java.util.List;

import io.github.privacystreamsevents.core.UQI;

/**
 * Get the emails from contact lists.
 */
class ContactEmailGetter extends ContactEmailProcessor<List<String>> {

    ContactEmailGetter(String emailsField) {
        super(emailsField);
    }

    @Override
    protected List<String> processCall(UQI uqi, List<String> emails) {
        if (emails == null) return null;
        return emails;
    }
}
