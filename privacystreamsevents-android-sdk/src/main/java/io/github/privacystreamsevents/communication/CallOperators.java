package io.github.privacystreamsevents.communication;


import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

/**
 * A helper class to access call-related operators.
 */
@PSOperatorWrapper
public class CallOperators {

    /**
     * Get the phone number from incoming calls.
     *
     * @return the function
     */
    public static Function<Item, String> callerIdentification() {
        String contactField = Call.CONTACT;
        return new CallNumberGetter(contactField);
    }

}