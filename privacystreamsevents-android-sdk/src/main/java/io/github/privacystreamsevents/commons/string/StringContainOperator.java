package io.github.privacystreamsevents.commons.string;

import io.github.privacystreamsevents.utils.Assertions;

/**
 * Check whether the string specified by a field contains a certain substring.
 */
final class StringContainOperator extends StringProcessor<Boolean> {

    private final String searchString;

    StringContainOperator(String stringField, String searchString) {
        super(stringField);
        this.searchString = Assertions.notNull("searchString", searchString);
        this.addParameters(searchString);
    }

    @Override
    protected Boolean processString(String stringValue) {
        return stringValue != null && stringValue.contains(this.searchString);
    }
}
