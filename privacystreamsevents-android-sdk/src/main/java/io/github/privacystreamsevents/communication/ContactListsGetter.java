package io.github.privacystreamsevents.communication;


import java.util.List;

import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.core.exceptions.PSException;
import io.github.privacystreamsevents.core.purposes.Purpose;

/**
 * Get a list of contact items.
 */
class ContactListsGetter extends ItemOperator<List<Item>> {

    @Override
    public List<Item> apply(UQI uqi, Item input) {
        List<Item> contactLists = null;
        try {
            contactLists = uqi.getData(Contact.getAll(), Purpose.UTILITY("Get contact lists."))
                    .asList();
        } catch (PSException e) {
            e.printStackTrace();
        }
        return contactLists;
    }
}

