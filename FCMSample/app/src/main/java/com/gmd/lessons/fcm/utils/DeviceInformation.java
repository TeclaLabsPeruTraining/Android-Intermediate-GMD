package com.gmd.lessons.fcm.utils;

import android.os.Build;

import java.util.UUID;

/**
 * Created by emedinaa on 03/03/17.
 *
 System.getProperty("os.version"); // OS version
 android.os.Build.VERSION.SDK      // API Level
 android.os.Build.DEVICE           // Device
 android.os.Build.MODEL            // Model
 android.os.Build.PRODUCT          // Product
 */

public class DeviceInformation {

    private String product;
    private String model;
    private String device;
    private String versionSDK;
    private String os;
    private String deviceID;

    public DeviceInformation() {

        versionSDK= Integer.toString(Build.VERSION.SDK_INT);
        device= Build.DEVICE;
        model= Build.MODEL;
        product= Build.PRODUCT;
        os= "ANDROID";
        deviceID= buildDeviceID();
    }

    public String getProduct() {
        return product;
    }

    public String getModel() {
        return model;
    }

    public String getDevice() {
        return device;
    }

    public String getVersionSDK() {
        return versionSDK;
    }

    public String getOs() {
        return os;
    }

    public String getDeviceID() {
        return deviceID;
    }

    private String buildDeviceID(){
        return UUID.randomUUID().toString();
    }


}
