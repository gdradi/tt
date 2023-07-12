package com.technogym.mycycling.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.mycycling.managers.MyCyclingTestActionManager;

public interface IMyCyclingEquipmentBridge extends IEquipmentBridge<MyCyclingTestActionManager> {

    @JavascriptInterface
    public void scanBarCode();

    @JavascriptInterface
    public boolean isBluetoothConnected();

    @JavascriptInterface
    public void toggleBluetooth();

    @JavascriptInterface
    public void askForEquipmentFirmwareVersion();

    @JavascriptInterface
    public void askForBootloaderFirmwareVersion();

    @JavascriptInterface
    public boolean isBleConnected();

    @JavascriptInterface
    public void upgradeEquipmentFirmware(String firmwareFilePath);

    @JavascriptInterface
    public void startBootloaderMode();

    @JavascriptInterface
    public void reconnectBle();

    @JavascriptInterface
    public void disconnectEquipment();

    @JavascriptInterface
    public void askForUpdatedFirmwareVersion();
}
