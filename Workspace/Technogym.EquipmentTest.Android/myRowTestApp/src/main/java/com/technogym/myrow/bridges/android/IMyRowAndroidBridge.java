package com.technogym.myrow.bridges.android;

import android.webkit.JavascriptInterface;

public interface IMyRowAndroidBridge extends IAndroidBridge {

    @JavascriptInterface
    public void setUser(final String username);

    @JavascriptInterface
    public void executeNetworkBackupLog();

    @JavascriptInterface
    public void setEquipmentSerialNumer(final String serialNumber);

    @JavascriptInterface
    public void setEquipmentBluetoothName(String equipmentBluetoothName);

    @JavascriptInterface
    public void searchDevice(String deviceName);

    @JavascriptInterface
    public boolean connectToBleDevice(String deviceName);

    @JavascriptInterface
    public void setEquipmentFirmwareVersion(final String equipmentVersion);

}
