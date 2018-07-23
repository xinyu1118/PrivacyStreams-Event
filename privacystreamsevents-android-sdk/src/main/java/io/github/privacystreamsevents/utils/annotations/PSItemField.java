package io.github.privacystreamsevents.utils.annotations;

import java.lang.annotation.Documented;

/**
 * An annotation representing an item field
 */
@Documented
public @interface PSItemField {
    Class type();
}
