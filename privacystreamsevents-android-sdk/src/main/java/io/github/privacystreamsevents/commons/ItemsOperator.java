package io.github.privacystreamsevents.commons;

import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;

import java.util.List;

/**
 * A function that takes a list of items as input.
 */

public abstract class ItemsOperator<Tout> extends Function<List<Item>, Tout> {
}
