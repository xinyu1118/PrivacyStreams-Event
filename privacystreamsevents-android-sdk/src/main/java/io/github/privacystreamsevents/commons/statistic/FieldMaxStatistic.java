package io.github.privacystreamsevents.commons.statistic;

import io.github.privacystreamsevents.utils.StatisticUtils;

import java.util.List;

/**
 * Get the max value of a field in the stream.
 */
final class FieldMaxStatistic extends FieldStatistic<Number> {

    FieldMaxStatistic(String numField) {
        super(numField);
    }

    @Override
    protected Number processNumList(List<Number> numList) {
        return StatisticUtils.max(numList);
    }
}
