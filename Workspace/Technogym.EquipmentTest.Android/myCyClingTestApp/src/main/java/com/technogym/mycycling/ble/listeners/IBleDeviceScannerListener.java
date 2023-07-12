package com.technogym.mycycling.ble.listeners;

import com.technogym.mycycling.listeners.IListener;

/***
 * Method to notify the device scanner's events
 *
 * @author Federico Foschini
 */
public interface IBleDeviceScannerListener extends IListener {

    /***
     * Method to notify the device found event
     * @param data: parameter data to notify
     */
    public void targetDeviceFound(String data);
}
