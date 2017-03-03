package com.gmd.lessons.fcm.storage.model;

/**
 * Created by emedinaa on 02/03/17.
 */

/*
{
 "deviceToken" : value,
 "deviceId" : value,
 "os" : "IOS" | "ANDROID" | "WP",
 "osVersion": value
 [,"channels": [channelName1, channelName2]],
 [,"expiration": timestamp in GMT0]
}
 */
public class DeviceRaw {
    public static  final String ANDROIDOS="ANDROID";

    private String deviceToken;
    private String deviceId;
    private String os=ANDROIDOS;
    private String osVersion;

    public DeviceRaw() {
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
}
