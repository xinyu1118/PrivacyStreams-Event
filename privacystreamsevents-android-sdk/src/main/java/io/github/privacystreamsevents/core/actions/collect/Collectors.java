package io.github.privacystreamsevents.core.actions.collect;

import io.github.privacystreamsevents.core.Callback;
import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.PStream;
import io.github.privacystreamsevents.core.PStreamAction;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

import java.util.List;

/**
 * A helper class to access stream collectors.
 */
@PSOperatorWrapper
public class Collectors {

    /**
     * Collect the items in the stream with a collector function, and handle the result with another function.
     *
     * @param itemsCollector the function to collect the items
     * @param resultHandler the function to handle result
     * @param <Tout> the output type of collector
     * @return the function
     */
    public static <Tout> PStreamAction collectItems(Function<List<Item>, Tout> itemsCollector,
                                                    Callback<Tout> resultHandler) {
        return new PStreamCollector<>(itemsCollector, resultHandler);
    }

    /**
     * Collect the items in the stream with a collector function.
     *
     * @param itemsCollector the function to collect the items
     * @return the function
     */
    public static PStreamAction collectItems(Function<List<Item>, Void> itemsCollector) {
        return new PStreamCollector<>(itemsCollector, null);
    }

    /**
     * Collect the PStream to a list of Items.
     * @return the function
     */
    public static Function<PStream, List<Item>> toItemList() {
        return new MStreamToItemListCollector();
    }
}
