package com.technogym.mycycling.ble.controllers;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.util.Log;

import com.technogym.mycycling.ble.listeners.IBleControllerListener;
import com.technogym.mycycling.ble.listeners.IBleDeviceScannerListener;
import com.technogym.mycycling.ble.listeners.UartRxReadListener;
import com.technogym.mycycling.ble.models.BleDevice;
import com.technogym.mycycling.ble.models.BleUUIDs;
import com.technogym.mycycling.ble.threads.UartRxReader;
import com.technogym.mycycling.ble.utilities.BleDeviceScanner;
import com.technogym.mycycling.ble.utilities.BleUtils;
import com.technogym.mycycling.ble.threads.UartTxWriter;
import com.technogym.mycycling.ble.listeners.UartTxWriterListener;
import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.commands.factories.CommandReplyFactory;
import com.technogym.mycycling.support.ConversionHelper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import it.spot.android.logger.domain.Logger;

/***
 * Bluetooth Low Energy Controller
 *
 * @author Federico Foschini
 * @version 1.0
 */
public class BleController extends BluetoothGattCallback
        implements IBleController, UartTxWriterListener, UartRxReadListener, IBleDeviceScannerListener {

    // { Private fields

    private static int FAKE_ERROR_133_TO_SKIP  = 133;

    private String riddenValue = "";

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;

    protected BleDevice mTargetBleDevice;
    protected IBleControllerListener mBleControllerNotificable;
    protected Context mContext;
    protected List<BleDevice> mDevices;
    protected BleDeviceScanner mBleDeviceScanner;
    protected HashMap<UUID, BluetoothGattCharacteristic> mGattCharacteristics;

    protected String operationTag;
    protected UartTxWriter bleWriter;
    protected UartRxReader bleReader;

    // }

    // { Constructors

    public BleController(Context context, IBleControllerListener bleControllerNotificable) {
        mContext = context;
        mBleControllerNotificable = bleControllerNotificable;
        mDevices = new ArrayList<BleDevice>();
        mGattCharacteristics = new HashMap<UUID, BluetoothGattCharacteristic>();
        operationTag = "";

        // For API level 18 and above, get a reference to BluetoothAdapter through BluetoothManager.
        if (mBluetoothManager == null) {
            mBluetoothManager = BleUtils.getManager(mContext);
            if (mBluetoothManager == null) {
                Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Unable to initialize BluetoothManager.");
            }
        }

        // bluetooth adapter
        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Unable to obtain a BluetoothAdapter.");
        }

        // bluetooth le scanner
        mBleDeviceScanner = new BleDeviceScanner(mContext, this, this);
    }

    // }

    // { Internal methods

    private boolean refreshDeviceCache(BluetoothGatt gatt){
        try {
            BluetoothGatt localBluetoothGatt = gatt;
            Method localMethod = localBluetoothGatt.getClass().getMethod("refresh", new Class[0]);
            if (localMethod != null) {
                boolean bool = ((Boolean) localMethod.invoke(localBluetoothGatt, new Object[0])).booleanValue();
                Log.i(this.getClass().getSimpleName(), "[refreshDeviceCache] Refresh device cache result: " + bool);
                return bool;
            }
        }
        catch (Exception localException) {
            Log.e(this.getClass().getSimpleName(), "[refreshDeviceCache] An exception occured while refreshing device");
        }
        return false;
    }

    // }

    // { Public methods

    @Override
    public boolean isScanning() { return mBleDeviceScanner.isScanning(); }

    @Override
    public int getConnectionState() { return mConnectionState; }

    @Override
    public boolean connect(final String address) {
        if (mBluetoothAdapter == null || address == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (mBluetoothDeviceAddress != null && address.equals(mBluetoothDeviceAddress) && mBluetoothGatt != null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGatt.connect()) {
                mConnectionState = STATE_CONNECTING;
                return true;
            } else {
                return false;
            }
        }

        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Device not found.  Unable to connect.");
            return false;
        }
        mTargetBleDevice = new BleDevice(device, 0);
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(mContext, false, this);
        //refreshDeviceCache(mBluetoothGatt);
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Trying to create a new connection.");
        mBluetoothDeviceAddress = address;
        mConnectionState = STATE_CONNECTING;
        return true;
    }

    @Override
    public void reconnect() {
        //refreshDeviceCache(mBluetoothGatt);
        connect(mTargetBleDevice.getDevice().getAddress());
        //mBluetoothGatt = mTargetBleDevice.getDevice().connectGatt(mContext, false, this);
    }

    @Override
    public void disconnect() {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "BluetoothAdapter not initialized.");
            return;
        }
        mBluetoothGatt.disconnect();
    }

    @Override
    public void toggleBleConnection() {
        if(mConnectionState == STATE_CONNECTED) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "disconnecting ble equipment...");
            disconnect();
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "ble equipment disconnected");
        } else {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "connecting ble equipment: " + (mTargetBleDevice != null));
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "connecting ble equipment: " + mTargetBleDevice.getDevice().getName());
            mBluetoothGatt.connect();
            //mBluetoothGatt = mTargetBleDevice.getDevice().connectGatt(mContext, false, this);
            //refreshDeviceCache(mBluetoothGatt);
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "ble equipment disconnected");
        }
    }

    @Override
    public void close() {
        if (mBluetoothGatt == null) {
            return;
        }
        mBluetoothGatt.close();
        mBluetoothGatt = null;
    }

    @Override
    public void readCharacteristic(UUID characteristicUUID, String tag) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "BluetoothAdapter not initialized.");
            return;
        }

        operationTag = tag;
        Log.i(this.getClass().getSimpleName(), "Read characteristic for operation: " + operationTag);
        BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(characteristicUUID);
        //Log.i(this.getClass().getSimpleName(), "target characteristic: " + (characteristic != null));
        try {
            boolean resRead = mBluetoothGatt.readCharacteristic(characteristic);
            Log.i(this.getClass().getSimpleName(), "Read characteristic for [" + tag + "] - result: " + resRead);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "Read characteristic for [" + tag + "] - error: " + ex.getMessage());
        }
    }

    @Override
    public void setCharacteristicNotification(UUID characteristicUUID, boolean enabled) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Logger.getInstance().logDebug(this.getClass().getSimpleName(), "BluetoothAdapter not initialized.");
            return;
        }

        // find the target characteristic
        BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(characteristicUUID);
        //Log.i(this.getClass().getSimpleName(), "target characteristic found: " + (characteristic != null));
        boolean notificationEnabled = mBluetoothGatt.setCharacteristicNotification(characteristic, enabled);

        // if the characteristic has been found, request the read operation
        if(characteristic != null) {

            // Enabled remote notifications
            BluetoothGattDescriptor desc = characteristic.getDescriptor(BleUUIDs.UUID_RX_CONFIG_DESCRIPTOR);
            Log.i(this.getClass().getSimpleName(), "target characteristic notification has descriptor: " + (desc != null));
            if(desc != null) {
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            } else {
                desc = new BluetoothGattDescriptor(BleUUIDs.UUID_RX_CONFIG_DESCRIPTOR, BluetoothGattDescriptor.PERMISSION_READ);
                desc.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                characteristic.addDescriptor(desc);
            }
            boolean notificationRes = mBluetoothGatt.writeDescriptor(desc);
            Log.i(this.getClass().getSimpleName(), "target characteristic set notification result: " + notificationRes + " - " + notificationEnabled);
            Log.i(this.getClass().getSimpleName(), "target characteristic set notification properties: " + characteristic.getProperties());

            //Log.i(this.getClass().getSimpleName(), "target characteristic notification result: " + notificationEnabled);

            //boolean test = mBluetoothGatt.readDescriptor(desc);
            //Log.i(this.getClass().getSimpleName(), "read descriptor result: " + test);
        }
    }

    // }

    // { BluetoothGattCallback implementation

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.i(this.getClass().getSimpleName(), "on services discovered with status " + status);

        if(status != BluetoothGatt.GATT_SUCCESS) {
            Log.w(this.getClass().getSimpleName(), "onServicesDiscovered received: " + status);
            mBluetoothGatt.discoverServices();
            return;
        }

        for(BluetoothGattService srv : gatt.getServices()) {
            if ((srv == null) || (srv.getUuid() == null)) {
                continue;
            }

            /* Log.i(this.getClass().getSimpleName(),
                "on services discovered - discovered the device info services: " +
                    (BleUUIDs.UUID_DEVICE_INFORMATION_SERVICE.toString().equalsIgnoreCase(srv.getUuid().toString()))); */

            BluetoothGattCharacteristic curChar = null;
            if (BleUUIDs.UUID_DEVICE_INFORMATION_SERVICE.toString().equalsIgnoreCase(srv.getUuid().toString())) {

                Log.i(this.getClass().getSimpleName(),
                        "on services discovered - Serial Number Characteristic found");

                curChar = srv.getCharacteristic(BleUUIDs.UUID_SERIAL_NUMBER_CHAR);
                mGattCharacteristics.put(BleUUIDs.UUID_SERIAL_NUMBER_CHAR, curChar);
                //setCharacteristicNotification(BleUUIDs.UUID_SERIAL_NUMBER_CHAR, true);

                /*Log.i(this.getClass().getSimpleName(),
                        "on services discovered - Firmware Version Characteristic found");

                curChar = srv.getCharacteristic(BleUUIDs.UUID_FIRMWARE_VERSION_CHAR);
                mGattCharacteristics.put(BleUUIDs.UUID_FIRMWARE_VERSION_CHAR, curChar);*/
                //setCharacteristicNotification(BleUUIDs.UUID_FIRMWARE_VERSION_CHAR, true);

            } else if (BleUUIDs.UUID_UART_TX_SERVICE.toString().equalsIgnoreCase(srv.getUuid().toString())) {

                if(mGattCharacteristics.get(BleUUIDs.UUID_RX_SERVICE) == null) {

                    Log.i(this.getClass().getSimpleName(), "on services discovered - RX service found");

                    curChar = srv.getCharacteristic(BleUUIDs.UUID_RX_SERVICE);
                    Log.i(this.getClass().getSimpleName(), "RX Characteristic Property: " + curChar.getProperties());
                    mGattCharacteristics.put(BleUUIDs.UUID_RX_SERVICE, curChar);
                    setCharacteristicNotification(BleUUIDs.UUID_RX_SERVICE, true);
                }

                if (mGattCharacteristics.get(BleUUIDs.UUID_TX_SERVICE) == null) {

                    Log.i(this.getClass().getSimpleName(), "on services discovered - TX service found");
                    curChar = srv.getCharacteristic(BleUUIDs.UUID_TX_SERVICE);
                    Log.i(this.getClass().getSimpleName(), "TX Characteristic Property: " + curChar.getProperties());
                    mGattCharacteristics.put(BleUUIDs.UUID_TX_SERVICE, curChar);
                    //setCharacteristicNotification(BleUUIDs.UUID_TX_SERVICE, true);
                }
            }
        }
        mBleControllerNotificable.onConnectionStateChange(mConnectionState);
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "on connection state changed to status " + newState + " - Gatt Result: " + status);
        Log.i(this.getClass().getSimpleName(), "on connection state changed to status " + newState + " - Gatt Result: " + status);

        // native fake errors to skip check
        if(status != BluetoothGatt.GATT_SUCCESS && status == FAKE_ERROR_133_TO_SKIP) {
            return;
        }

        if (newState == BluetoothProfile.STATE_CONNECTED) {
            gatt.connect();
            //refreshDeviceCache(gatt);
            mConnectionState = STATE_CONNECTED;
            if(mGattCharacteristics.isEmpty()) {
                gatt.discoverServices();
            } else {
                mBleControllerNotificable.onConnectionStateChange(mConnectionState);
            }
        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            mConnectionState = STATE_DISCONNECTED;
            mBleControllerNotificable.onConnectionStateChange(mConnectionState);
        } else {
            mConnectionState = STATE_CONNECTING;
            mBleControllerNotificable.onConnectionStateChange(mConnectionState);
        }
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        //Log.i(this.getClass().getSimpleName(), "on characteristic write for tag: " + operationTag + " with status " + status);
        bleWriter.goWrite();
        //currentWritingStep = currentWritingStep + 1;
        //writeSingleBleData(characteristic, currentWritingData, currentWritingStep);
        super.onCharacteristicWrite(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.i(this.getClass().getSimpleName(), "On Read characteristic for [" + operationTag + "] - result: " + status);
        ICommandReply reply = CommandReplyFactory.create(operationTag, ConversionHelper.BytesToString(characteristic.getValue()));
        //Log.i(this.getClass().getSimpleName(), "on characteristic read status success: " + (status == BluetoothGatt.GATT_SUCCESS));
        if (status == BluetoothGatt.GATT_SUCCESS) {
            mBleControllerNotificable.onValueReceived(operationTag, reply);
        }
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        //characteristic.getValue()

        String data = ConversionHelper.BytesToString(characteristic.getValue());
        Log.i(this.getClass().getSimpleName(), "Changed Characteristic for [" + operationTag + "] - Value: " + data);
        //riddenValue = riddenValue + data;
        bleReader.readData(characteristic.getValue());
    }

    // }

    // { IBleController implementation

    @Override
    public void searchDevice(String deviceName) { mBleDeviceScanner.startScan(deviceName); }

    @Override
    public BleDevice getBleDevice(String deviceName) {
        BleDevice target = null;
        //Log.i(this.getClass().getSimpleName(), "devices list: " + mDevices.size());
        Log.i(this.getClass().getSimpleName(), "searching the device: " + deviceName);
        for (BleDevice bleDevice : mDevices) {
            //Log.i(this.getClass().getSimpleName(), "current device: " + bleDevice.getDevice().getName());
            if(bleDevice.getDevice().getName().toUpperCase().equals(deviceName.toUpperCase())) {
                target = bleDevice;
                break;
            }
        }
        Log.i(this.getClass().getSimpleName(), "device: " + deviceName + " -> " + (target != null ? "found" : "not found"));
        return target;
    }

    @Override
    public BleDevice getConnectedBleDevice() { return mTargetBleDevice; }

    @Override
    public void setBleDevice(BleDevice device) {
        mTargetBleDevice = device;
    }

    @Override
    public boolean isBleSupported() {
        return BleUtils.isBLESupported(mContext);
    }

    @Override
    public boolean isBleConnected() { return mConnectionState == STATE_CONNECTED; }

    @Override
    public void addBleDevice(BleDevice device) {
        mDevices.add(device);
        Log.i(this.getClass().getSimpleName(), "Adding Device -> " + device.getDevice().getName());
        //Log.i(this.getClass().getSimpleName(), "Adding Device -> " + device.getDevice().getAddress());
        //Log.i(this.getClass().getSimpleName(), "Device List -> " + mDevices.size());
    }

    @Override
    public boolean isDeviceFound(String deviceName) {
        boolean found = false;
        for (BleDevice bleDevice : mDevices) {
            if(bleDevice.getDisplayName().toUpperCase().equals(deviceName.toUpperCase())) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    public void stopBleDeviceSearch() {
        mBleDeviceScanner.stopScan();
    }

    @Override
    public void writeBleData(UUID characteristiUUI, String tag, String data) {
        Log.i(this.getClass().getSimpleName(), "Write data for tag: " + tag);
        operationTag = tag;
        byte[] dataToWrite = data.getBytes();
        BluetoothGattCharacteristic characteristic = mGattCharacteristics.get(characteristiUUI);
        bleWriter = new UartTxWriter(mBluetoothGatt, this, characteristic, tag, dataToWrite);
        new Thread(bleWriter).start();
        bleReader = new UartRxReader(this, tag);
        new Thread(bleReader).start();
    }

    // }

    // { IBleDeviceScannerListener implementation

    @Override
    public void targetDeviceFound(String data) { mBleControllerNotificable.onTargetDeviceFound(data); }

    // }

    // { UartTxWriterListener implementation

    @Override
    public void onWriteCompleted(boolean writeResult, String tag) {
        Log.i(this.getClass().getSimpleName(), "Write Data Completed for tag: " + tag + " with result: " + writeResult);
        //readCharacteristic(BleUUIDs.UUID_RX_SERVICE, tag);
    }

    // }

    // { UartRxReaderListener implementation

    @Override
    public void onReadComplete(String data, String tag) {
        Log.i(this.getClass().getSimpleName(), "Ridden Data Completed for tag: " + tag + " with result: " + data);
        bleReader.forceEnd();
        mBleControllerNotificable.onValueReceived(tag, CommandReplyFactory.create(tag, data));
    }

    // }
}
