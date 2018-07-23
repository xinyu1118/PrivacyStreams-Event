package io.github.privacystreamsevents.core;


/**
 * Audio callback data containing several fine-grained fields.
 */
public class AudioCallbackData extends CallbackData {

    /**
     * Intermediate data to be called back, average loudness in dB.
     */
    public Double avgLoudness;

    /**
     * Intermediate data to be called back, maximum loudness in dB.
     */
    public Double maxLoudness;

    /**
     * Intermediate data to be called back, such as average or maximum loudness in dB.
     */
    public Double fieldValue;


    public AudioCallbackData() {
        this.TIME_CREATED = System.currentTimeMillis();
    }

    public void setAvgLoudness(Double avgLoudness) {
        this.avgLoudness = avgLoudness;
    }

    public void setMaxLoudness(Double maxLoudness) {
        this.maxLoudness = maxLoudness;
    }

    public void setFieldValue(Double fieldValue) {
        this.fieldValue = fieldValue;
    }

    public void setEventType(String eventType) {
        this.EVENT_TYPE = eventType;
    }

}
