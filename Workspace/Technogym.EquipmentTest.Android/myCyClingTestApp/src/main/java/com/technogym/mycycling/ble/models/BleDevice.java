package com.technogym.mycycling.ble.models;

import android.bluetooth.BluetoothDevice;

/***
 * Bluetooth device modeling
 *
 * @author Federico Foschini
 * @version 1.0
 */
public class BleDevice {

    // { Private fields

    private static final String UNKNOWN = "Unknown";
    private BluetoothDevice mDevice;
    private int mRssi;
    private String mDisplayName;

    // }

    // { Constructors

    public BleDevice(BluetoothDevice device, int rssi) {
        if (device == null) {
            throw new IllegalArgumentException("BluetoothDevice is null");
        }
        mDevice = device;
        mDisplayName = device.getName();
        if ((mDisplayName == null) || (mDisplayName.length() == 0)) {
            mDisplayName = UNKNOWN;
        }
        mRssi = rssi;
    }

    // }

    // { Public methods

    public BluetoothDevice getDevice() {
        return mDevice;
    }

    public int getRssi() {
        return mRssi;
    }

    public void setRssi(int rssi) {
        mRssi = rssi;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public void setDisplayName(String displayName) {
        mDisplayName = displayName;
    }

    // }
}
