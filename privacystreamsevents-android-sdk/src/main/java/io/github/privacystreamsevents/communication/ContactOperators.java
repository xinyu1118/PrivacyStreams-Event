package io.github.privacystreamsevents.communication;


import java.util.List;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access contact-related operators.
 */
@PSOperatorWrapper
public class ContactOperators {

    /**
     * Get the email list from all contacts.
     *
     * @return the function
     */
    public static Function<Item, List<String>> getContactEmails() {
        String emailsField = Contact.EMAILS;
        return new ContactEmailGetter(emailsField);
    }

    /**
     * Get the phone list from all contacts.
     *
     * @return the function
     */
    public static Function<Item, List<String>> getContactPhones() {
        String phonesField = Contact.PHONES;
        return new ContactPhoneGetter(phonesField);
    }

    /**
     * Get contact lists.
     *
     * @return the function
     */
    public static Function<Item, List<Item>> getContactLists() {
        return new ContactListsGetter();
    }

}
