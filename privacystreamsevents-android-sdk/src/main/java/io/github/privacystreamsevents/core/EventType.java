package io.github.privacystreamsevents.core;


import android.content.Context;

import java.util.List;

/**
 * Abstract class for all events of personal data, including audio, location, contact, message, file.
 * Concrete event inherits from it and implements specific monitoring methods.
 */
public abstract class EventType {

    public static final Integer AlwaysRepeat = 0;

    public static final Long Off = new Integer(0).longValue();

    public static final String DefaultPrecision = "defaultPrecision";

    /**
     * Listen to audio event that happens repeatedly.
     */
    public static final String Audio_Repeated_Event = "audioRepeatedEvent";

    /**
     * Listen to audio event that happens only once.
     */
    public static final String Audio_OneTime_Event = "audioOneTimeEvent";

    /**
     * Check high loudness recorded from microphone once,
     * the loudness is the average value of recorded audio data in dB.
     */
//    public static final String Audio_Check_Average_Loudness = "audioCheckAvgLoudness";

    /**
     * Check high loudness recorded from microphone periodically,
     * the loudness is the average value of recorded audio data in dB.
     */
//    public static final String Audio_Check_Average_Loudness_Periodically = "audioCheckAvgLoudnessPeriodically";

    /**
     * Obtain the maximum loudness (dB) in a recorded audio datum once,
     * and check if it's over the specific threshold.
     */
//    public static final String Audio_Check_Maximum_Loudness = "audioCheckMaxLoudness";

    /**
     * Obtain a series of maximum loudness (dB) recorded from microphone periodically,
     * and check if each of them is over the specific threshold.
     */
//    public static final String Audio_Check_Maximum_Loudness_Periodically = "audioCheckMaxLoudnessPeriodically";

    /**
     * Check whether the user is talking or not from microphone.
     */
//    public static final String Audio_Has_Human_Voice = "audioHasHumanVoice";

    /**
     * Audio customized event with field value defined by developers.
     */
//    public static final String Audio_Customized_Event = "audioCustomizedEvent";

    /**
     * Keep monitoring if a user is entering/leaving a specified circle area,
     * which is defined by the center latitude, longitude and radius.
     */
    public static final String Geolocation_GeoFence = "geoLocationGeoFence";

    /**
     * Check the current coordinate in or out of a specific place,
     * such as carnegie mellon university.
     */
    public static final String Geolocation_Check_Location_In_Places = "geoLocationCheckLocationInPlaces";

    /**
     * Monitor the latitude and longitude updates, different location precision provided.
     */
    public static final String Geolocation_Location_Updated = "geolocationLocationUpdated";

    /**
     * Keep monitoring over speed event, speed is measured in m/s.
     */
    public static final String Geolocation_Check_Speed = "geoLocationCheckSpeed";

    /**
     * Keep monitoring local city changes.
     */
    public static final String Geolocation_City_Change = "geoLocationCityChange";

    /**
     * Keep monitoring local postcode changes.
     */
    public static final String Geolocation_Postcode_Change = "geoLocationPostcodeChange";

    /**
     * Monitor the direction changes e.g. turn left or right using bearing.
     * Bearing is the horizontal direction of travel of this device, and is not related to the device orientation.
     * It is guaranteed to be in the range (0.0, 360.0].
     */
    public static final String Geolocation_Making_Turns = "geolocationMakingTurns";

    /**
     * Keep monitoring if a user has arrived a destination,
     * which is defined by latitude and longitude.
     */
    public static final String Geolocation_Arrive_Destination = "geoLocationArriveDestination";

    /**
     * Monitor incoming phone calls, and check if it comes from a certain contact.
     */
    public static final String Call_Check_Unwanted = "callCheckUnwanted";

    /**
     * Check call logs, and judge whether there are unwanted calls from a certain contact,
     * both caller name and phone number are supported.
     */
    public static final String Call_Logs_Checking = "callLogsChecking";

    /**
     * Monitor incoming calls and check the phone number is in a list.
     */
    public static final String Call_In_List = "callInList";
    //public static final String Call_In_Blacklist = "callInBlacklist";

    /**
     * Monitor incoming calls and check the phone number is from contact lists.
     */
    //public static final String Call_From_Contacts = "callFromContacts";

    /**
     * Monitor incoming calls in the background.
     */
    public static final String Call_Coming_In = "callComingIn";

    /**
     * Check contact lists updates, including adding, deleting, modifying.
     */
    public static final String Contact_Lists_Updated = "contactListsUpdated";

    /**
     * Get email addresses for all contacts on the device, and
     * check if any of them is in a existing email list.
     */
    public static final String Contact_Emails_In_Lists = "contactEmailsInLists";

    /**
     * Check messages, and judge whether there are unwanted messages from a certain contact,
     * both caller name and phone number are supported.
     */
    public static final String Message_Check_Unwanted = "messageCheckUnwanted";

    /**
     * Monitor incoming messages and check the phone number is in a list.
     */
    public static final String Message_In_List = "messageInList";
    //public static final String Message_In_Blacklist = "messageInBlacklist";

    /**
     * Monitor incoming messages and check the phone number is from contact lists.
     */
    //public static final String Message_From_Contacts = "messageFromContacts";

    /**
     * Check message content updates, including adding, deleting, modifying.
     */
    public static final String Message_Lists_Updated = "messageListsUpdated";

    /**
     * Monitor incoming messages in the background.
     */
    public static final String Message_Coming_In = "messageComingIn";

    /**
     * Detect faces in an image from storage, return true if there is at least one face in an image.
     */
    public static final String Image_Has_Face = "imageHasFace";

    /**
     * Monitor image content changes, including creating, deleting, modifying etc.
     */
    public static final String Image_Content_Updated = "imageContentUpdated";

    /**
     * Monitor a folder content changes, including creating, deleting, modifying etc.
     */
    public static final String Image_FileOrFolder_Updated = "imageFileOrFolderUpdated";

    /**
     * Deal with multiple events, 'and', 'or', and 'not' logic operations are supported.
     */
    public static final String Event_Collections = "eventCollections";

    // if the event happens periodically, its value turns true by subclasses, otherwise false.
    public boolean periodicEvent;

    public abstract void setEventType(String eventType);

    public abstract String getEventType();

    public abstract <T> void setField(String fieldName, Function<Item, T> fieldCalculationFunction);

    public abstract String getFieldName();

    public abstract void setComparator(String comparator);

    public abstract String getComparator();

    public abstract void setDuration(long duration);

    public abstract long getDuration();

    public abstract void setInterval(long interval);

    public abstract long getInterval();

    public abstract void setMaxNumberOfRecurrences(Integer recurrence);

    public abstract Integer getMaxNumberOfRecurrences();

    public abstract void setFieldConstraints(Double threshold);

    public abstract Double getFieldConstraints();

    public abstract void setLocationPrecision(String locationPrecision);

    public abstract String getLocationPrecision();

    public abstract void setLatitude(Double latitude);

    public abstract Double getLatitude();

    public abstract void setLongitude(Double longitude);

    public abstract Double getLongitude();

    public abstract void setRadius(Double radius);

    public abstract Double getRadius();

    public abstract void setPlaceName(String placeName);

    public abstract String getPlaceName();

    public abstract void setLists(List<String> lists);

    public abstract List<String> getLists();

    public abstract void setCaller(String caller);

    public abstract String getCaller();

    public abstract void setPath(String path);

    public abstract String getPath();

    public abstract void and(List<EventType> andEvents);

    public abstract List<EventType> getAndEvents();

    public abstract void or(List<EventType> orEvents);

    public abstract List<EventType> getOrEvents();

    public abstract void not(List<EventType> notEvents);

    public abstract List<EventType> getNotEvents();

    public abstract void setSatisfyCond();

    public abstract boolean getSatisfyCond();

    public abstract void setBroadListener(BroadListener broadListener);

    public abstract void addOptimizationConstraints(List<List> batteryIntervalMatrix);

    /**
     * Abstract method, implemented by subclasses to handle specific events,
     * current context is passed as the only parameter.
     */
    public abstract void handle(Context context, EventCallback psCallback);

}
