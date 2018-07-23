package io.github.privacystreamsevents.commons.statistic;

import io.github.privacystreamsevents.utils.StatisticUtils;

import java.util.List;

/**
 * Get the mode value of a field in the stream.
 */
final class FieldModeStatistic extends FieldStatistic<Number> {

    FieldModeStatistic(String numField) {
        super(numField);
    }

    @Override
    protected Number processNumList(List<Number> numList) {
        return StatisticUtils.mode(numList);
    }
}
