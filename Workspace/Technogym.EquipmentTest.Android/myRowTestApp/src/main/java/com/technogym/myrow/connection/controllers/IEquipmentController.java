package com.technogym.myrow.connection.controllers;

import com.technogym.myrow.commands.models.ICommand;
import com.technogym.myrow.connection.listeners.EquipmentConnectionListener;
import com.technogym.myrow.connection.models.EquipmentConnectionState;
import com.technogym.myrow.exceptions.ConnectionException;

import java.util.List;

public interface IEquipmentController {

    // { Connection

    /***
     * Method to search a device
     * @param deviceName: device to search
     */
    public void searchDevice(String deviceName);

    /***
     * Method to notify the device found event
     * @param data: parameter data to notify
     */
    public void onTargetDeviceFound(String data);

    /***
     * Connect to the equipment
     * @param address: equipment's address to connect
     * return true if connection has been done, otherwise false
     */
    public boolean connect(String address);

    /**
     * Reconnets the GATT Bluetooth controller
     */
    public void reconnect();

    /**
     * Disconnects from the equipment
     */
    public void disconnect();

    /***
     * Check if the Ble is connected
     * @return true if Ble is connected, otherwise false
     */
    public boolean isBleConnected();

    /**
     * Method to set get searched ble device
     * @param deviceName: device to check's name
     * @return the searched device
     */
    //public BleDevice getBleDevice(String deviceName);

    /***
     * Toggle the ble connection
     */
    public void toggleBleConnection();

    // }

    // { Interaction layer between equipment connection controller and UI

    /**
     * Sends a message to the equipment
     * @param msgCommand: message command to send
     * @throws ConnectionException: error occurred's exception
     */
    public void sendCommand(ICommand msgCommand) throws ConnectionException;

    /**
     * Registers a listener for connection
     * @param listener: listener to register
     */
    public void registerEquipmentConnectionListener(final EquipmentConnectionListener listener);

    /**
     * Unregisters a listener for connection
     * @param listener: listener to unregister
     */
    public void unregisterEquipmentConnectionListener(final EquipmentConnectionListener listener);

    // }

    // { Getters and setters

    /***
     * Gets the connection state
     * @return the connection state
     */
    public EquipmentConnectionState getConnectionState();

    /***
     * Sets the connection state
     * @param state: the connection state
     */
    public void setConnectionState(final EquipmentConnectionState state);

    /***
     * Gets the connections' listeners
     * @return the listeners registered to connection events
     */
    public List<EquipmentConnectionListener> getRegisteredListeners();

    // }
}
