package io.github.privacystreamsevents.commons.statistic;

import io.github.privacystreamsevents.utils.StatisticUtils;

import java.util.List;

/**
 * Get the median value of a field in the stream.
 */
final class FieldMedianStatistic extends FieldStatistic<Number> {

    FieldMedianStatistic(String numField) {
        super(numField);
    }

    @Override
    protected Number processNumList(List<Number> numList) {
        return StatisticUtils.median(numList);
    }
}
