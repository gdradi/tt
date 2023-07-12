package com.technogym.mycycling.bridges.web;

import android.webkit.JavascriptInterface;

import com.technogym.mycycling.connection.listeners.IMyCyclingListener;

public interface IMyCyclingJavascriptBridge extends IJavascriptBridge, IMyCyclingListener {

    /**
     * This method handles the ble device found event.
     *
     * @param data
     *            : the content which indicates the result of the scan operation
     * */
    @JavascriptInterface
    public void onBleDeviceFound(final String data);

    /**
     * This method handles the ble connection state change event.
     * */
    @JavascriptInterface
    public void onBleConnectionStateChanged();

    /**
     * This method handles the upgrade checked event.
     *
     * @param upgradeRequired
     *            : the flag related to the upgrade required
     * */
    @JavascriptInterface
    public void onEquipmentFirmwareUpgradeChecked(final Boolean upgradeRequired);

    /**
     * This method handles the firmware downloaded event.
     *
     * @param downloadFilePath
     *            : the path of the firmware downloaded
     * */
    @JavascriptInterface
    public void onEquipmentFirmwareDownloaded(String downloadFilePath);

    /**
     * This method handles the start of the bootloader mode event.
     *
     * @param bootloaderModeStarted
     *            : the flag related to the start of this mode
     * */
    @JavascriptInterface
    public void onBootloaderModeStarted(final Boolean bootloaderModeStarted);

    /**
     * This method handles the update of the firmware version.
     *
     * @param firmwareVersion
     *             : updated firmware version
     */
    @JavascriptInterface
    public void onEquipmentFirmwareVersionUpdated(String firmwareVersion);
}
