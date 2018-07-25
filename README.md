# PrivacyStreamsEvents

---

PrivacyStreamsEvents is an Android library that helps developers handle events for personal data access contexts in an easy and privacy-friendly way. It offers a functional programming model for various types of personal data, including audios, locations, contacts, messages, images and extensible to more. In PrivacyStreams Events, all types of personal data access and processing events could be generated using builder pattern, afterwards handled with a uniform query interface (UQI).

    EventType event = new Event.EventBuilder()
                    .setEventDescription()
                    .setField()
                    .setComparator()
                    .setFieldConstraints()
                    .setSamplingMode()
                    .setMaxNumOfRecurrences()
                    .addOptimizationConstraints()
                    .build();
    UQI.addEventListener(event, new EventCallback(){
            @Override
            public void onEvent(CallbackData callbackData) {
            }
    });
    
Where, an event consists of several elements, including event description, field value, comparator, field constraints, sampling mode, max number of recurrences and optimization strategies. Not all elements are required. *Event description* (optional) is a short sentence used to remind developers of what the event is. *Field* (required) is the fine-grained data that should be processed in the event, and it's calculated with built-in or user defined functions. *Comparator* (required) refers to the specific operators that should be carried on the *Field* value. *Field constraints* (required if applicable) supplies other conditions to form an event, which could be omitted, or contain several parameter settings according to programming needs. *Sampling mode* (required if applicable) defines how to sample data, such as interval, duration, precision etc. *Max number of recurrences* (required) is how many times developers would like the event happens, which could be always repeated, or limited number (especially 1 means the event could happen only once). *Optimization constraints* (optional) specify optimization strategies to save power.

In PrivacyStreams Events, all developers should do is to find out proper functions to form an event, and then handle it using a uniform query interface. If the event happens, callback data will be returned to apps for subsequent procedures.

Based on the functions used in the query, PrivacyStreams Events is able to generate a privacy description about what granularity of data and when they are accessed, which could be used for the app description or privacy policy.

**Quick examples**
---

**Monitor the surrounding noise level.**

    /**
     * Check the average loudness calculated from audio data every 10s 
     * for 1s, and return callbacks to apps when it is lower than or equal 
     * to 30dB repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.RECORD_AUDIO" />
     */
    EventType audioEvent = new AudioEvent.AudioEventBuilder()
            .setField("loudness", AudioOperators.calcAvgLoudness(Audio.AUDIO_DATA))
            .setComparator(AudioEvent.LTE)
            .setFieldConstraints(30.0)
            .setSamplingMode(10000, 1000)
            .setMaxNumberOfRecurrences(EventType.AlwaysRepeat)
            .build();
    uqi.addEventListener(audioEvent, new AudioCallback() {
        @Override
        public void onEvent(AudioCallbackData audioCallbackData) {
            Log.d("Callback", String.valueOf(audioCallbackData.fieldValue));
        }
    });
        
 **Check the user local place.**   

    /**
     * Check the location every 10s and return callbacks to apps if the 
     * user is in the given place repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
     */
    EventType locationEvent = new GeolocationEvent.GeolocationEventBuilder()
            .setField("location", GeolocationOperators.getLatLon())
            .setComparator(GeolocationEvent.IN)
            .setPlaceName("Newell Simon Hall")
            .setSamplingMode(10000)
            .setMaxNumberOfRecurrences(EventType.AlwaysRepeat)
            .build();
    uqi.addEventListener(locationEvent, new GeolocationCallback() {
        @Override
        public void onEvent(GeolocationCallbackData geolocationCallbackData) {
            Log.d("Callback", String.valueOf(geolocationCallbackData.currentTime));
        }
    }); 
    
**Check the emails of contact lists.**  

    /**
     * Check the emails of contacts, and return callbacks to apps if there 
     * are emails in the given list repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.READ_CONTACTS" />
     */
    List<String> emailLists = new ArrayList<>();
    emailLists.add("psevent@gmail.com");
    emailLists.add("test@gmail.com");
    EventType contactEvent = new ContactEvent.ContactEventBuilder()
            .setField("emails", ContactOperators.getContactEmails())
            .setComparator(ContactEvent.IN)
            .setContactList(emailLists)
            .setMaxNumberOfRecurrences(1)
            .build();
    uqi.addEventListener(contactEvent, new ContactCallback() {
        @Override
        public void onEvent(ContactCallbackData contactCallbackData) {
            List<String> pendingEmails = contactCallbackData.emails;
            if (pendingEmails != null) {
                for (String email : pendingEmails) {
                    Log.d("Callback", email);
                }
            }
        }
    });

**Monitor calls from a caller.**  

    /**
     * Listen to incoming calls and return callbacks if they are from a 
     * certain phone number repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.READ_PHONE_STATE" />
     * <uses-permission android:name="android.permission.PROCESS_OUTGOING_CALLS" />
     */
    EventType callEvent = new ContactEvent.ContactEventBuilder()
            .setField("caller", CallOperators.callerIdentification())
            .setComparator(ContactEvent.EQ)
            .setPhoneNumber("15555215556")
            .setMaxNumberOfRecurrences(EventType.AlwaysRepeat)
            .build();
    uqi.addEventListener(callEvent, new ContactCallback() {
        @Override
        public void onEvent(ContactCallbackData contactCallbackData) {
        }
    });

**Monitor messages from a sender.**  
    
    /**
     * Monitor incoming messages and return callbacks if they are from a 
     * certain phone number repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.RECEIVE_SMS" />
     */
    EventType messageEvent = new MessageEvent.MessageEventBuilder()
            .setField("sender", MessageOperators.getMessagePhones())
            .setComparator(MessageEvent.EQ)
            .setPhoneNumber("15555215556")
            .setMaxNumberOfRecurrences(EventType.AlwaysRepeat)
            .build();
    uqi.addEventListener(messageEvent, new MessageCallback() {
        @Override
        public void onEvent(MessageCallbackData messageCallbackData) {
            Log.d("Callback", String.valueOf(messageCallbackData.TIME_CREATED));
        }
    });
    
**Monitor image content updates.**  

    /**
     * Monitor image content and return callbacks to apps when it is updated 
     * repeatedly.
     * Make sure the following line is added to AndroidManifest.xml
     * <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
     */
    EventType imageEvent = new ImageEvent.ImageEventBuilder()
            .setField("images", ImageOperators.getImageData())
            .setComparator(ImageEvent.UPDATED)
            .setMaxNumberOfRecurrences(EventType.AlwaysRepeat)
            .build();
    uqi.addEventListener(imageEvent, new ImageCallback() {
        @Override
        public void onEvent(ImageCallbackData imageCallbackData) {

        }
    });
    
**Installation**
---    

**Option 1. Using Gradle (recommended for the most users)**

Step 1. Add the JitPack repository to your build file
Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. Add the dependency

	dependencies {
        implementation 'com.github.xinyu1118:PrivacyStreamsEvents:v0.1.1'
    }


That's it!

Note that if you want to **use Google location service** instead of the Android location service, you will need one more step.

The Location APIs in PrivacyStreams Events are based on Google location service. In order to access location with PrivacyStreams Events, you will need to install Google location service. 

Specifically, add the following line to `build.gradle` file under tha app module.

    dependencies {
        ...
        compile 'com.google.android.gms:play-services-location:10.2.1'
        ...
    }
    
Then in your app code, enable Google location service:

    Globals.LocationConfig.useGoogleService = true;
    

**Option 2. From source (recommended for contributors)**

In Android Studio, the installation involves the following steps:

1 Clone the project to your computer.

2 Open your own project, import privacysecurer-android-sdk module.

2.1）Click File -> New -> Import module     
2.2）Select `privacystreamsevents-android-sdk` directory as the source directory

3 In your app module, add the following line to dependencies:
`implementation project(':privacystreamsevents-android-sdk')`

**Documentation**
---  
[LINK][1]

**Other Information**
---
PrivacySecurer is initially developed by Xinyu Yang, under the advisory of Jason Hong and Yuvraj Agarwal, depending on CHIMPS Lab at Carnegie Mellon University and National Engineering Lab for Mobile Network Technologies at Beijing University of Posts and Telecommunications.

Contact Author (xinyuy2@andrew.cmu.edu)


  [1]: https://docs.google.com/document/d/13eyGI1-gqV4eXm467RjF6H9XuQYtx-tYG1vwPmzaT04/edit#heading=h.8bthu2z2dnv8