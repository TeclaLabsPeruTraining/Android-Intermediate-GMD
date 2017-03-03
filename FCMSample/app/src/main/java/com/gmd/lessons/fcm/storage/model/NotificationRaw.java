package com.gmd.lessons.fcm.storage.model;

import java.util.HashMap;

/**
 * Created by emedinaa on 03/03/17.
 {
 "message" : value,
 ["publisherId" : value,]
 "headers": {"key1":"value1","key2":"value2"}
 ["pushPolicy":"ONLY" | "ALSO",]
 ["pushBroadcast" : IOS | ANDROID | WP | ALL,] // the value is a mask
 ["pushSinglecast": ["regId1", "regId2",] // the value is an array
 ["publishAt":timestamp,] // the value in milliseconds
 ["repeatEvery":frequency-in-seconds,]
 ["repeatExpiresAt":expiration-timestamp] // the value is to be in milliseconds
 }
 */

public class NotificationRaw {
    //private final String PUSH_POLICY_ONLY= "ONLY";
    //private final String PUSH_POLICY_ALSO= "ALSO";

    //private final String PUSH_BOARDCAST_ANDROID= "ANDROID";

    private String message;
    private String publisherId;
    private HashMap<String,String> headers; //json
    private String pushPolicy;
    private String pushBroadcast;
    private String[] pushSinglecast;
    private String publishAt;

    public NotificationRaw() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(String publisherId) {
        this.publisherId = publisherId;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public String getPushPolicy() {
        return pushPolicy;
    }

    public void setPushPolicy(String pushPolicy) {
        this.pushPolicy = pushPolicy;
    }

    public String getPushBroadcast() {
        return pushBroadcast;
    }

    public void setPushBroadcast(String pushBroadcast) {
        this.pushBroadcast = pushBroadcast;
    }

    public String[] getPushSinglecast() {
        return pushSinglecast;
    }

    public void setPushSinglecast(String[] pushSinglecast) {
        this.pushSinglecast = pushSinglecast;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public void buildHeader(String mTitle, String mMessage, String mTicker){
        this.headers= new HashMap<>();
        this.headers.put("android-ticker-text",mTicker);
        this.headers.put("android-content-text",mMessage);
        this.headers.put("android-content-title",mTitle);
    }
}
