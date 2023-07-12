package com.technogym.equipmenttest.bleuart.fitnessmachineservice.interfaces;

import android.bluetooth.BluetoothGatt;

import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.FitnessBtDevice;


/**
 * Created by sventurini on 24/04/16.
 */
public interface ConnectionListener {

    /**
     *
     * @param gatt
     * @param device
     */
    void connected(BluetoothGatt gatt, FitnessBtDevice device);

    /**
     *
     */
    void disconnected();

    /**
     *
     * @param device
     */
    void configured(FitnessBtDevice device);

}
