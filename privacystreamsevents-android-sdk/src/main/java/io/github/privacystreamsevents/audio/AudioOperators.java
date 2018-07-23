package io.github.privacystreamsevents.audio;


import io.github.privacystreamsevents.core.Function;
import io.github.privacystreamsevents.core.Item;
import io.github.privacystreamsevents.utils.annotations.PSOperatorWrapper;

import java.util.List;

/**
 * A helper class to access audio-related operators.
 */
@PSOperatorWrapper
public class AudioOperators {

    /**
     * Calculate the average (RMS) loudness of the audio specified by an AudioData field.
     * The loudness is an double number indicating the sound pressure level in dB.
     *
     * @param audioDataField the name of the AudioData field.
     * @return the function.
     */
    public static Function<Item, Double> calcAvgLoudness(String audioDataField) {
        return new AudioLoudnessCalculator(audioDataField);
    }

    /**
     * Calculate the maximum loudness of the audio specified by an AudioData field.
     * The loudness is an double number indicating the sound pressure level in dB.
     *
     * @param audioDataField the name of the AudioData field.
     * @return the function.
     */
    public static Function<Item, Double> calcMaxLoudness(String audioDataField) {
        return new AudioMaxLoudnessCalculator(audioDataField);
    }

    /**
     * Developers could specify their own functions here to calculate a customized field value
     * of the audio specified by an AudioData field.
     *
     * @param audioDataField the name of the AudioData field.
     * @return the function.
     */
//    public static Function<Item, Double> customizedFunctions(String audioDataField) {
//        return new CustomizedFieldGenerator(audioDataField);
//    }

    /**
     * Get the max amplitude of the audio specified by an AudioData field.
     * The amplitude is an Integer from 0 to 32767.
     *
     * @param audioDataField the name of the AudioData field.
     * @return the function.
     */
    public static Function<Item, Integer> getMaxAmplitude(String audioDataField) {
        return new AudioMaxAmplitudeGetter(audioDataField);
    }

    /**
     * Get the amplitude samples of the audio specified by an AudioData field.
     * Each amplitude sample is an Integer from 0 to 32767.
     * The amplitude sampling rate can be configured at `Globals.AudioConfig.amplitudeSamplingRate`;
     *
     * @param audioDataField the name of the AudioData field.
     * @return the function.
     */
    public static Function<Item, List<Integer>> getAmplitudeSamples(String audioDataField) {
        return new AudioAmplitudeSamplesGetter(audioDataField);
    }

    /**
     * Calculate loudness (in decibels) based on an amplitude value.
     * The amplitude should be a number from 0 to 32767.
     *
     * @param amplitudeField the name of the amplitude field.
     * @return the function.
     */
    public static Function<Item, Double> convertAmplitudeToLoudness(String amplitudeField) {
        return new AmplitudeToLoudnessConverter(amplitudeField);
    }

    /**
     * Get the file path of the audio specified by an AudioData field.
     * The path might point to a temporary audio file if it is not from storage.
     * To permanently save the file, you need to copy the file to another file path.
     *
     * @param audioDataField the name of AudioData field
     * @return the function
     */
    public static Function<Item, String> getFilepath(String audioDataField) {
        return new AudioFilepathGetter(audioDataField);
    }

    /**
     * Detect human voice from the audio.
     * Return true if there is human voice.
     *
     * @param audioDataField the name of AudioData field
     * @return the function
     */
    public static Function<Item, Boolean> hasHumanVoice(String audioDataField) {
        return new AudioVoiceDetector(audioDataField);
    }

}
