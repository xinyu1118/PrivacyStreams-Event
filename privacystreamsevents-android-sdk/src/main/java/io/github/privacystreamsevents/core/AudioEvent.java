package io.github.privacystreamsevents.core;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import io.github.privacystreamsevents.audio.Audio;
import io.github.privacystreamsevents.audio.AudioOperators;
import io.github.privacystreamsevents.core.exceptions.PSException;
import io.github.privacystreamsevents.core.purposes.Purpose;
import io.github.privacystreamsevents.utils.Consts;

import static android.content.Context.BATTERY_SERVICE;
import static io.github.privacystreamsevents.utils.Assertions.notNull;

/**
 * Audio related events, used for setting event parameters and providing processing methods.
 */
public class AudioEvent<TValue> extends EventType {
    // Field name options
//    public static final String AvgLoudness = "avgLoudness";
//    public static final String MaxLoudness = "maxLoudness";

    // Operator options
    public static final String GTE = "gte";
    public static final String LTE = "lte";
    public static final String GT = "gt";
    public static final String LT = "lt";
    public static final String EQ = "eq";
    public static final String NEQ = "neq";

    /**
     * The boolean flag used to indicate whether the event is triggered or not,
     * its initial value is false.
     */
    public boolean satisfyCond = false;
    private BroadListener broadListener;

    /**
     * Event type defined in EventType class.
     */
    private String eventType;
    /**
     * The field name of personal data.
     */
    private String fieldName;
    /**
     * The field value calculation function.
     */
    private Function<Item, TValue> fieldCalculationFunction;
    /**
     * The comparator on the field value.
     */
    private String comparator;
    /**
     * The threshold to be compared with.
     */
    private Double threshold;
    /**
     * The duration of audio recording in milliseconds.
     */
    private long duration;
    /**
     * The interval of audio recording in milliseconds.
     */
    private long interval;
    /**
     * The event recurrence times, could be positive integer value representing the event happens limited times,
     * or AlwaysRepeat meaning it happens uninterruptedly.
     */
    private Integer recurrence;
    /**
     * A matrix setting the sampling interval values in various sections, the elements in each row
     * are upper bound, lower bound and interval in turn.
     */
    private List<List> optimizationMatrix = new ArrayList<>();

    /**
     * Intermediate data to be returned, average loudness in dB.
     */
//    private Double avgLoudness;
    /**
     * Intermediate data to be returned, maximum loudness in dB.
     */
//    private Double maxLoudness;
    /**
     * Intermediate data to be returned, user defined field value.
     */
    private Double fieldValue;

    // used to count the event occurrence times
    int counter = 0;
    private static Object monitor = new Object();
    private static boolean isCharged = false;
    private boolean broadcastRegistered = false;
    BroadcastReceiver receiver;
    Context mContext;


    @Override
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    @Override
    public String getEventType() {
        return this.eventType;
    }

    @Override
    public <T> void setField(String fieldName, Function<Item, T> fieldCalculationFunction) {
        this.fieldName = fieldName;
        this.fieldCalculationFunction = (Function<Item, TValue>) fieldCalculationFunction;
    }

    @Override
    public String getFieldName() {
        return this.fieldName;
    }

    @Override
    public void setComparator(String comparator) {
        this.comparator = comparator;
    }

    @Override
    public String getComparator() {
        return this.comparator;
    }

    @Override
    public void setDuration(long duration) {
        this.duration = duration;
    }

    @Override
    public long getDuration() {
        return this.duration;
    }

    @Override
    public void setInterval(long interval) {
        this.interval = interval;
    }

    @Override
    public long getInterval() {
        return this.interval;
    }

    @Override
    public void setMaxNumberOfRecurrences(Integer recurrence) {
        this.recurrence = recurrence;
    }

    @Override
    public Integer getMaxNumberOfRecurrences() {
        return this.recurrence;
    }

    @Override
    public void setFieldConstraints(Double threshold) {
        this.threshold = threshold;
    }

    @Override
    public Double getFieldConstraints() {
        return this.threshold;
    }

    @Override
    public void setLocationPrecision(String locationPrecision) {

    }

    @Override
    public String getLocationPrecision() {
        return null;
    }

    @Override
    public void setLatitude(Double latitude) {

    }

    @Override
    public Double getLatitude() {
        return null;
    }

    @Override
    public void setLongitude(Double longitude) {

    }

    @Override
    public Double getLongitude() {
        return null;
    }

    @Override
    public void setRadius(Double radius) {

    }

    @Override
    public Double getRadius() {
        return null;
    }

    @Override
    public void setPlaceName(String placeName) {

    }

    @Override
    public String getPlaceName() {
        return null;
    }

    @Override
    public void setLists(List<String> lists) {

    }

    @Override
    public List<String> getLists() {
        return null;
    }

    @Override
    public void setCaller(String caller) {

    }

    @Override
    public String getCaller() {
        return null;
    }

    @Override
    public void setPath(String path) {

    }

    @Override
    public String getPath() {
        return null;
    }

    @Override
    public void and(List<EventType> andEvents) {

    }

    @Override
    public List<EventType> getAndEvents() {
        return null;
    }

    @Override
    public void or(List<EventType> orEvents) {

    }

    @Override
    public List<EventType> getOrEvents() {
        return null;
    }

    @Override
    public void not(List<EventType> notEvents) {

    }

    @Override
    public List<EventType> getNotEvents() {
        return null;
    }

    @Override
    public void setSatisfyCond() {
        this.satisfyCond = true;
        if (broadListener != null) {
            if (satisfyCond) {
                broadListener.onSuccess();
            } else {
                broadListener.onFail("Receive failed response.");
            }
        }
    }

    @Override
    public boolean getSatisfyCond() {
        return this.satisfyCond;
    }

    @Override
    public void setBroadListener(BroadListener broadListener) {
        this.broadListener = broadListener;
    }

    @Override
    public void addOptimizationConstraints(List<List> optimizationMatrix) {
        this.optimizationMatrix = optimizationMatrix;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void handle(Context context, final EventCallback eventCallback) {
        UQI uqi = new UQI(context);
        mContext = context;
        final AudioCallbackData audioCallbackData = new AudioCallbackData();

//        switch (fieldName) {
//            case AvgLoudness:
//                if (recurrence == 1)
//                    this.setEventType(EventType.Audio_Check_Average_Loudness);
//                else
//                    this.setEventType(EventType.Audio_Check_Average_Loudness_Periodically);
//                break;
//            case MaxLoudness:
//                if (recurrence == 1)
//                    this.setEventType(EventType.Audio_Check_Maximum_Loudness);
//                else
//                    this.setEventType(EventType.Audio_Check_Maximum_Loudness_Periodically);
//                break;
//        }
        audioCallbackData.setEventType(eventType);

        // Handle events according to event type
        switch (eventType) {
            case EventType.Audio_OneTime_Event:
                periodicEvent = false;

                if (fieldName == null) Log.d(Consts.LIB_TAG, "You haven't set field yet, it couldn't be null.");
                if (comparator == null) Log.d(Consts.LIB_TAG, "You haven't set comparator yet, it couldn't be null.");
                if (threshold == null) Log.d(Consts.LIB_TAG, "You haven't set threshold yet, it couldn't be null.");
                if (duration == 0) Log.d(Consts.LIB_TAG, "The sampling duration is 0, it doesn't make sense.");

                try {
                    fieldValue = uqi.getData(Audio.record(duration), Purpose.UTILITY("Listen to "+fieldName+" one time."))
                            .setField(fieldName, fieldCalculationFunction)
                            .getFirst(fieldName);
                } catch (PSException e) {
                    e.printStackTrace();
                }

                switch (comparator) {
                    case GTE:
                        if (fieldValue >= threshold) {
                            Log.d(Consts.LIB_TAG, fieldName+" is greater than or equal to the threshold.");
                            setSatisfyCond();
                            //Toast.makeText(context, " Average loudness is higher than the threshold. ", Toast.LENGTH_SHORT).show();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    case LTE:
                        if (fieldValue <= threshold) {
                            Log.d(Consts.LIB_TAG, fieldName+" is lower than or equal to the threshold.");
                            setSatisfyCond();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    case GT:
                        if (fieldValue > threshold) {
                            Log.d(Consts.LIB_TAG, fieldName+" is greater than the threshold.");
                            setSatisfyCond();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    case LT:
                        if (fieldValue < threshold) {
                            Log.d(Consts.LIB_TAG, fieldName+" is lower than the threshold.");
                            setSatisfyCond();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    case EQ:
                        if (fieldValue.equals(threshold)) {
                            Log.d(Consts.LIB_TAG, fieldName+" is equal to the threshold.");
                            setSatisfyCond();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    case NEQ:
                        if (!fieldValue.equals(threshold)) {
                            Log.d(Consts.LIB_TAG, fieldName+" isn't equal to the threshold.");
                            setSatisfyCond();
                        } else
                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                        break;
                    default:
                        Log.d(Consts.LIB_TAG, "No matchable operators, please check it.");
                }

                audioCallbackData.setFieldValue(fieldValue);
                eventCallback.setAudioCallbackData(audioCallbackData);
                break;

            case EventType.Audio_Repeated_Event:
                periodicEvent = true;

                if (fieldName == null) Log.d(Consts.LIB_TAG, "You haven't set field yet, it couldn't be null.");
                if (comparator == null) Log.d(Consts.LIB_TAG, "You haven't set comparator yet, it couldn't be null.");
                if (threshold == null) Log.d(Consts.LIB_TAG, "You haven't set threshold yet, it couldn't be null.");
                if (duration == 0) Log.d(Consts.LIB_TAG, "The sampling duration is 0, it doesn't make sense.");
                if (interval == 0) Log.d(Consts.LIB_TAG, "The sampling interval is 0, please ensure it.");
                if (recurrence == null) Log.d(Consts.LIB_TAG, "You haven't set recurrence yet, it couldn't be null.");

                // Get the current battery level
                BatteryManager bm = (BatteryManager)context.getSystemService(BATTERY_SERVICE);
                int batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);

                // Add duration or interval settings based on the battery level
                if (optimizationMatrix != null) {
                    for (int i=0; i<optimizationMatrix.size(); i++) {

                        if ((Integer)optimizationMatrix.get(i).get(0) >= batteryLevel &&
                                (Integer)optimizationMatrix.get(i).get(1) <= batteryLevel) {
                            if (optimizationMatrix.get(i).get(2) != EventType.Off) {
                                interval = (Long)optimizationMatrix.get(i).get(2);

                                if (optimizationMatrix.get(i).size() == 4)
                                    duration = (Long)optimizationMatrix.get(i).get(3);

                            }
                            else {
                                // get current charging status
                                IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
                                Intent batteryStatus = context.registerReceiver(null, intentFilter);
                                int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
                                boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                                        status == BatteryManager.BATTERY_STATUS_FULL;
                                // if the device is charging, just sample data immediately, otherwise
                                // sleep until it is charged.
                                if (!isCharging) {
                                    Log.d(Consts.LIB_TAG, "Event will be paused until getting charged.");
                                    new WaitThread().start();
                                    new NotifyThread().start();
                                }
                            }
                            // If found a satisfied section, just break the loop. In this way,
                            // the boundary value could also be processed appropriately.
                            break;
                        }
                    }
                }

                final PStreamProvider pStreamProvider = Audio.recordPeriodic(duration, interval);
                uqi.getData(pStreamProvider, Purpose.UTILITY("Listen to "+fieldName+" periodically."))
                        .setField(fieldName, fieldCalculationFunction)
                        .forEach(fieldName, new Callback<Double>() {
                            @Override
                            protected void onInput(Double fieldValue) {
                                switch (comparator) {
                                    case GTE:
                                        if (fieldValue >= threshold) {
                                            counter ++;
                                            // Stop the monitoring thread when the event has happened recurringNumber times.
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence) {
                                                //Log.d(Consts.LIB_TAG, "No notification will be returned, the monitoring thread has been stopped.");
                                                pStreamProvider.isCancelled = true;
                                            } else {
                                                Log.d(Consts.LIB_TAG, fieldName+" is greater than or equal to the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    case LTE:
                                        if (fieldValue <= threshold) {
                                            counter ++;
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence)
                                                pStreamProvider.isCancelled = true;
                                            else {
                                                Log.d(Consts.LIB_TAG, fieldName+" is lower than or equal to the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    case GT:
                                        if (fieldValue > threshold) {
                                            counter ++;
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence)
                                                pStreamProvider.isCancelled = true;
                                            else {
                                                Log.d(Consts.LIB_TAG, fieldName+" is greater than the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    case LT:
                                        if (fieldValue < threshold) {
                                            counter ++;
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence)
                                                pStreamProvider.isCancelled = true;
                                            else {
                                                Log.d(Consts.LIB_TAG, fieldName+" is lower than the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    case EQ:
                                        if (fieldValue.equals(threshold)) {
                                            counter ++;
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence)
                                                pStreamProvider.isCancelled = true;
                                            else {
                                                Log.d(Consts.LIB_TAG, fieldName+" is equal to the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    case NEQ:
                                        if (!fieldValue.equals(threshold)) {
                                            counter ++;
                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence)
                                                pStreamProvider.isCancelled = true;
                                            else {
                                                Log.d(Consts.LIB_TAG, fieldName+" isn't equal to the threshold.");
                                                audioCallbackData.setFieldValue(fieldValue);
                                                eventCallback.setAudioCallbackData(audioCallbackData);
                                                setSatisfyCond();
                                            }
                                        } else {
                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
                                            satisfyCond = false;
                                        }
                                        break;
                                    default:
                                        Log.d(Consts.LIB_TAG, "No matchable operators, please check it.");
                                }
                            }
                        });

                if (broadcastRegistered)
                    mContext.unregisterReceiver(receiver);
                break;

            default:
                Log.d(Consts.LIB_TAG, "No audio event matches your input, please check it.");



//            case EventType.Audio_Check_Average_Loudness:
//                periodicEvent = false;
//
//                try {
//                    avgLoudness = uqi.getData(Audio.record(duration), Purpose.UTILITY("Listen to average audio loudness once."))
//                            .setField("avgLoudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
//                            .getFirst("avgLoudness");
//                } catch (PSException e) {
//                    e.printStackTrace();
//                }
//
//                switch (comparator) {
//                    case GTE:
//                        if (avgLoudness >= threshold) {
//                            Log.d(Consts.LIB_TAG, "Average loudness is greater than or equal to the threshold.");
//                            setSatisfyCond();
//                            //Toast.makeText(context, " Average loudness is higher than the threshold. ", Toast.LENGTH_SHORT).show();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    case LTE:
//                        if (avgLoudness <= threshold) {
//                            Log.d(Consts.LIB_TAG, "Average loudness is lower than or equal to the threshold.");
//                            setSatisfyCond();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    case GT:
//                        if (avgLoudness > threshold) {
//                            Log.d(Consts.LIB_TAG, "Average loudness is greater than the threshold.");
//                            setSatisfyCond();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    case LT:
//                        if (avgLoudness < threshold) {
//                            Log.d(Consts.LIB_TAG, "Average loudness is lower than the threshold.");
//                            setSatisfyCond();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    case EQ:
//                        if (avgLoudness.equals(threshold)) {
//                            Log.d(Consts.LIB_TAG, "Average loudness is equal to the threshold.");
//                            setSatisfyCond();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    case NEQ:
//                        if (!avgLoudness.equals(threshold)) {
//                            Log.d(Consts.LIB_TAG, "Average loudness isn't equal to the threshold.");
//                            setSatisfyCond();
//                        } else
//                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                        break;
//
//                    default:
//                        Log.d(Consts.LIB_TAG, "No matchable operators, please check it.");
//
//                }
//                audioCallbackData.setAvgLoudness(avgLoudness);
//                eventCallback.setAudioCallbackData(audioCallbackData);
//                break;
//
//            case EventType.Audio_Check_Average_Loudness_Periodically:
//                periodicEvent = true;
//
//                // Get the current battery level
//                BatteryManager bm = (BatteryManager)context.getSystemService(BATTERY_SERVICE);
//                int batteryLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
//
//                // Add interval settings based on the battery level
//                if (optimizationMatrix != null) {
//                    for (int i=0; i<optimizationMatrix.size(); i++) {
//                       if ((Integer)optimizationMatrix.get(i).get(0) >= batteryLevel &&
//                               (Integer)optimizationMatrix.get(i).get(1) <= batteryLevel) {
//                           if (optimizationMatrix.get(i).get(2) != EventType.Off) {
//                               interval = (Long)optimizationMatrix.get(i).get(2);
//                           }
//                           else {
//                               // get current charging status
//                               IntentFilter intentFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//                               Intent batteryStatus = context.registerReceiver(null, intentFilter);
//                               int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
//                               boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
//                                       status == BatteryManager.BATTERY_STATUS_FULL;
//
//                               // if the device is charging, just sample data immediately, otherwise
//                               // sleep until it is charged.
//                               if (!isCharging) {
//                                   Log.d(Consts.LIB_TAG, "Event will be paused until getting charged.");
//                                   new WaitThread().start();
//                                   new NotifyThread().start();
//                               }
//                           }
//                           // If found a satisfied section, just break the loop. In this way,
//                           // the boundary value could also be processed appropriately.
//                           break;
//                       }
//                    }
//                }
//
//                final PStreamProvider pStreamProvider = Audio.recordPeriodic(duration, interval);
//                uqi.getData(pStreamProvider, Purpose.UTILITY("Listen to average audio loudness periodically."))
//                        .setField("avgLoudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
//                        .forEach("avgLoudness", new Callback<Double>() {
//                            @Override
//                            protected void onInput(Double avgLoudness) {
//                                switch (comparator) {
//                                    case GTE:
//                                        if (avgLoudness >= threshold) {
//                                            counter ++;
//                                            // Stop the monitoring thread when the event has happened recurringNumber times.
//                                            if (recurrence != EventType.AlwaysRepeat && counter > recurrence) {
//                                                //Log.d(Consts.LIB_TAG, "No notification will be returned, the monitoring thread has been stopped.");
//                                                pStreamProvider.isCancelled = true;
//                                            } else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness is greater than or equal to the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    case LTE:
//                                        if (avgLoudness <= threshold) {
//                                            counter ++;
//                                            if (recurrence != null && counter > recurrence)
//                                                pStreamProvider.isCancelled = true;
//                                            else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness is lower than or equal to the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    case GT:
//                                        if (avgLoudness > threshold) {
//                                            counter ++;
//                                            if (recurrence != null && counter > recurrence)
//                                                pStreamProvider.isCancelled = true;
//                                            else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness is greater than the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    case LT:
//                                        if (avgLoudness < threshold) {
//                                            counter ++;
//                                            if (recurrence != null && counter > recurrence)
//                                                pStreamProvider.isCancelled = true;
//                                            else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness is lower than the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    case EQ:
//                                        if (avgLoudness.equals(threshold)) {
//                                            counter ++;
//                                            if (recurrence != null && counter > recurrence)
//                                                pStreamProvider.isCancelled = true;
//                                            else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness is equal to the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    case NEQ:
//                                        if (!avgLoudness.equals(threshold)) {
//                                            counter ++;
//                                            if (recurrence != null && counter > recurrence)
//                                                pStreamProvider.isCancelled = true;
//                                            else {
//                                                Log.d(Consts.LIB_TAG, "Average loudness isn't equal to the threshold.");
//                                                audioCallbackData.setAvgLoudness(avgLoudness);
//                                                eventCallback.setAudioCallbackData(audioCallbackData);
//                                                setSatisfyCond();
//                                            }
//                                        } else {
//                                            Log.d(Consts.LIB_TAG, "Event hasn't happened yet.");
//                                            satisfyCond = false;
//                                        }
//                                        break;
//
//                                    default:
//                                        Log.d(Consts.LIB_TAG, "No matchable operators, please check it.");
//                                }
//
//                            }
//                        });
//
//                if (broadcastRegistered)
//                    mContext.unregisterReceiver(receiver);
//
//                break;
//
//
//            case EventType.Audio_Has_Human_Voice:
//                //TODO
//                break;
//
//            default:
//                Log.d(Consts.LIB_TAG, "No audio event matches your input, please check it.");
        }
    }

    /**
     * Builder pattern used to construct audio related events.
     */
    public static class AudioEventBuilder<TValue> {
        private String eventDescription;
        private String fieldName;
        private Function<Item, TValue> fieldCalculationFunction;
        private String comparator;
        private Double threshold;
        private long duration;
        private long interval;
        private Integer recurrence;
        List<List> optimizationMatrix = new ArrayList<>();

        public AudioEventBuilder setEventDescription(String eventDescription) {
            this.eventDescription = eventDescription;
            return this;
        }

        public <Tout> AudioEventBuilder setField(String fieldName, Function<Item, Tout> fieldCalculationFunction) {
            this.fieldName = fieldName;
            this.fieldCalculationFunction = (Function<Item, TValue>) notNull("fieldCalculationFunction", fieldCalculationFunction);
            return this;
        }

        public AudioEventBuilder setComparator(String comparator) {
            this.comparator = comparator;
            return this;
        }

        public AudioEventBuilder setFieldConstraints(Double threshold) {
            this.threshold = threshold;
            return this;
        }

        public AudioEventBuilder setSamplingMode(long...intervalOrDuration) {
            if (intervalOrDuration.length == 1) this.duration = intervalOrDuration[0];
            if (intervalOrDuration.length == 2) {
                this.interval = intervalOrDuration[0];
                this.duration = intervalOrDuration[1];
            }
            return this;
        }

//        public AudioEventBuilder setDuration(long duration) {
//            this.duration = duration;
//            return this;
//        }
//
//        public AudioEventBuilder setInterval(long interval) {
//            this.interval = interval;
//            return this;
//        }

        public AudioEventBuilder setMaxNumberOfRecurrences(Integer recurrence) {
            this.recurrence = recurrence;
            return this;
        }

        public AudioEventBuilder addOptimizationConstraints (int upperBound, int lowerBound, long...samplingMode) {
            List rowVector = new ArrayList<>();
            rowVector.add(upperBound);
            rowVector.add(lowerBound);
            for (long arg : samplingMode) {
                rowVector.add(arg);
            }
            optimizationMatrix.add(rowVector);
            return this;
        }
//        public AudioEventBuilder addOptimizationConstraints(int upperBound, int lowerBound, long intervalInSections) {
//            List rowVector = new ArrayList<>();
//            rowVector.add(upperBound);
//            rowVector.add(lowerBound);
//            rowVector.add(intervalInSections);
//            optimizationMatrix.add(rowVector);
//            return this;
//        }

        public EventType build() {
            AudioEvent audioEvent = new AudioEvent();

            if (fieldName != null) {
                audioEvent.setField(fieldName, fieldCalculationFunction);
            }

            if (comparator != null) {
                audioEvent.setComparator(comparator);
            }

            if (threshold != null) {
                audioEvent.setFieldConstraints(threshold);
            }

            if (duration != 0) {
                audioEvent.setDuration(duration);
            }

            if (interval != 0) {
                audioEvent.setInterval(interval);
            }

            if (recurrence != null) {
                audioEvent.setMaxNumberOfRecurrences(recurrence);
            }

            if (optimizationMatrix != null) {
                audioEvent.addOptimizationConstraints(optimizationMatrix);
            }

            // Judge event type
            if (recurrence == 1)
                audioEvent.setEventType(EventType.Audio_OneTime_Event);
            else
                audioEvent.setEventType(EventType.Audio_Repeated_Event);

            return audioEvent;
        }
    }

    // If the device is not charged, wait the thread until power is connected.
    public class WaitThread extends Thread {
        public void run() {
            while(!isCharged) {
                synchronized(monitor) {
                    try {
                        monitor.wait();
                    } catch(InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // Notify the thread to keep on executing subsequent codes if charged.
    public class NotifyThread extends Thread {
        public void run() {
            IntentFilter ifilter = new IntentFilter();
            ifilter.addAction(Intent.ACTION_POWER_CONNECTED);
            //ifilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
            receiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent.getAction().equals(Intent.ACTION_POWER_CONNECTED)) {
                        //Toast.makeText(context, "connected", Toast.LENGTH_SHORT).show();
                        isCharged = true;
                        synchronized (monitor) {
                            monitor.notifyAll();
                        }
                    }
                }
            };
            mContext.registerReceiver(receiver, ifilter);
            broadcastRegistered = true;
        }
    }

}
