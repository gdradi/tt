package com.technogym.mycycling.ble.controllers;

import com.technogym.mycycling.ble.models.BleDevice;

import java.util.UUID;

/***
 * Bluetooth Low Energy Controller Interface
 *
 * @author Federico Foschini
 * @version 1.0
 */
public interface IBleController {

    /**
     * Method to get the current connection's state
     * @return the current connection's state
     */
    public int getConnectionState();

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address);

    /**
     * Reconnets the GATT Bluetooth controller
     */
    public void reconnect();

    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect();

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close();

    /***
     * Check if a Ble device has been found
     * @param deviceName: device to check's name
     * @return true if that device has been found, otherwise false
     */
    public boolean isDeviceFound(String deviceName);

    /**
     * Method to check if the device is scanning
     * @return true if the scan has not stopped, otherwise false
     */
    public boolean isScanning();

    /***
     * Method to search a device
     * @param deviceName: device to search
     */
    public void searchDevice(String deviceName);

    /***
     * Method to stop the device scanning
     */
    public void stopBleDeviceSearch();

    /***
     * Check if the Ble is supported
     * @return true if Ble is supported, otherwise false
     */
    public boolean isBleSupported();

    /***
     * Check if the Ble is connected
     * @return true if Ble is connected, otherwise false
     */
    public boolean isBleConnected();

    /***
     * Toggle the ble connection
     */
    public void toggleBleConnection();

    /***
     * Add a Ble device
     * @param device: device to add
     */
    public void addBleDevice(BleDevice device);

    /**
     * Method to set get searched ble device
     * @param deviceName: device to check's name
     * @return the searched device
     */
    public BleDevice getBleDevice(String deviceName);

    /**
     * Method to get the connected ble device
     * @return the connected ble device
     */
    public BleDevice getConnectedBleDevice();

    /**
     * Method to set the target ble device
     * @param device: the target ble device
     */
    public void setBleDevice(BleDevice device);

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristicUUID The characteristic's UUID to read from.
     * @param tag The operation's tag.
     */
    public void readCharacteristic(UUID characteristicUUID, String tag);

    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristicUUID The characteristic's UUID to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(UUID characteristicUUID, boolean enabled);

    /**
     * Method to write data in a specific service BLE
     * @param characteristiUUI: characteristic to write's UUID
     * @param tag The operation's tag.
     * @param data: data to write
     */
    public void writeBleData(UUID characteristiUUI, String tag, String data);
}
