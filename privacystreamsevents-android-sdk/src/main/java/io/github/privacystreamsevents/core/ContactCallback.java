package io.github.privacystreamsevents.core;


/**
 * Contact and call related callbacks with intermediate data.
 */
public abstract class ContactCallback extends EventCallback {

    private ContactCallbackData contactCallbackData;


    @Override
    public void setAudioCallbackData(AudioCallbackData audioCallbackData) {

    }

    @Override
    public AudioCallbackData getAudioCallbackData() {
        return null;
    }

    @Override
    public void setGeolocationCallbackData(GeolocationCallbackData geolocationCallbackData) {

    }

    @Override
    public GeolocationCallbackData getGeolocationCallbackData() {
        return null;
    }

    @Override
    public void setContactCallbackData(ContactCallbackData contactCallbackData) {
        this.contactCallbackData = contactCallbackData;
    }

    @Override
    public ContactCallbackData getContactCallbackData() {
        return this.contactCallbackData;
    }

    @Override
    public void setMessageCallbackData(MessageCallbackData messageCallbackData) {

    }

    @Override
    public MessageCallbackData getMessageCallbackData() {
        return null;
    }

    @Override
    public void setImageCallbackData(ImageCallbackData imageCallbackData) {

    }

    @Override
    public ImageCallbackData getImageCallbackData() {
        return null;
    }

    @Override
    public void onEvent(AudioCallbackData audioCallbackData) {

    }

    @Override
    public void onEvent(GeolocationCallbackData geolocationCallbackData) {

    }

    @Override
    public void onEvent(MessageCallbackData messageCallbackData) {

    }

    @Override
    public void onEvent(ImageCallbackData imageCallbackData) {

    }
}
