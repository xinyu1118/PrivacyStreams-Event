package io.github.privacystreamsevents.audio;


import io.github.privacystreamsevents.commons.ItemOperator;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.core.UQI;
import io.github.privacystreamsevents.utils.Assertions;

/**
 * Process the audio field in an item.
 */
abstract class AudioProcessor<Tout> extends ItemOperator<Tout> {

    private final String audioDataField;

    AudioProcessor(String audioDataField) {
        this.audioDataField = Assertions.notNull("audioDataField", audioDataField);
        this.addParameters(audioDataField);
    }

    @Override
    public final Tout apply(UQI uqi, Item input) {
        AudioData audioData = input.getValueByField(this.audioDataField);
        return this.processAudio(uqi, audioData);
    }

    protected abstract Tout processAudio(UQI uqi, AudioData audioData);

}
