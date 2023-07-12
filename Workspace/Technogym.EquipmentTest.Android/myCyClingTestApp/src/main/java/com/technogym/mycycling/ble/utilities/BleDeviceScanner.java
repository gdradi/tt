package com.technogym.mycycling.ble.utilities;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.util.Log;

import com.technogym.mycycling.ble.controllers.IBleController;
import com.technogym.mycycling.ble.listeners.IBleDeviceScannerListener;
import com.technogym.mycycling.ble.models.BleDevice;

import java.util.Timer;
import java.util.TimerTask;

import it.spot.android.logger.domain.Logger;

/***
 * Scanner for Ble devices
 *
 * @author Federico Foschini
 * @version 1.0
 */
public class BleDeviceScanner implements BluetoothAdapter.LeScanCallback  {

    // { Private fields

    protected static final int SCANNING_TIMEOUT = 5000;

    protected Context mContext;
    protected IBleController mController;
    protected IBleDeviceScannerListener mBleDeviceScannerNotificable;
    protected boolean mIsScanning;
    protected BluetoothAdapter mBTAdapter;
    protected String mDeviceName;
    protected Timer mTimer;
    protected TimerTask mScanningTimerTask;

    // }

    // { Constructors

    public BleDeviceScanner(Context context, IBleController controller, IBleDeviceScannerListener bleDeviceScannerNotificable) {
        mContext = context;
        mController = controller;
        mBleDeviceScannerNotificable = bleDeviceScannerNotificable;
        mIsScanning = false;

        // BT check
        BluetoothManager manager = BleUtils.getManager(mContext);
        if (manager != null) {
            mBTAdapter = manager.getAdapter();
        }
    }

    // }

    // { Public methods

    /**
     * Method to scan searching a device
     * @param deviceName: target device's name
     */
    public void startScan(String deviceName) {
        Log.i(this.getClass().getSimpleName(), "Starting ble scanning...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Starting ble scanning...");

        // scanning timer timeout
        mTimer = new Timer();
        mScanningTimerTask = new TimerTask() {

            @Override
            public void run() {
                if(isScanning()) {
                    Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Scanning device timeout.");
                    mBleDeviceScannerNotificable.targetDeviceFound(Boolean.FALSE.toString());
                    stopScan();
                }
            }
        };
        mTimer.schedule(mScanningTimerTask, SCANNING_TIMEOUT);

        mDeviceName = deviceName != null && !deviceName.isEmpty() ? deviceName : "";
        if ((mBTAdapter != null) && (!mIsScanning)) {
            mBTAdapter.startLeScan(this);
            mIsScanning = true;
        }
    }

    /**
     * Method to check if the device is scanning
     * @return true if the scan has not stopped, otherwise false
     */
    public boolean isScanning() { return mIsScanning; }

    /**
     * Method to stop the device scan
     */
    public void stopScan() {
        Log.i(this.getClass().getSimpleName(), "Stopping ble scanning...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Stopping ble scanning...");
        if (mBTAdapter != null) {
            mBTAdapter.stopLeScan(this);
        }
        mIsScanning = false;
    }

    // }

    // { LeScanCallback implementation

    @Override
    public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
        Log.i(this.getClass().getSimpleName(), "onLeScan - A Device has been found [ " + (device.getName() != null ? device.getName() : "NO NAME") + "]");
        if (device != null && device.getName() != null && (device.getName().toUpperCase().contains("MYCYCLING") || device.getName().toUpperCase().contains("MYRIDE"))) {
            Log.i(this.getClass().getSimpleName(), "onLeScan - Device Found -> " + device.getName());
            Log.i(this.getClass().getSimpleName(), "onLeScan - Target Device -> " + mDeviceName);
            Log.i(this.getClass().getSimpleName(), "onLeScan - Found -> " + device.getName().toUpperCase().equals(mDeviceName.toUpperCase()));
            //Logger.getInstance().logDebug(this.getClass().getSimpleName(), "onLeScan - Device Found -> " + device.getName());
            mController.addBleDevice(new BleDevice(device, rssi));

            if(device.getName().toUpperCase().equals(mDeviceName.toUpperCase())) {
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "onLeScan - Target Device Found -> " + device.getName());
                mBleDeviceScannerNotificable.targetDeviceFound(Boolean.TRUE.toString());
                stopScan();
            }
        }
    }

    // }
}
