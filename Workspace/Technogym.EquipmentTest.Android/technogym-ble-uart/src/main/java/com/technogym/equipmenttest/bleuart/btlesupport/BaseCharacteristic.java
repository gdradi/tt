package com.technogym.equipmenttest.bleuart.btlesupport;

import android.Manifest;
import android.bluetooth.BluetoothGattCharacteristic;

import java.util.UUID;

/**
 * Created by sventurini on 20/07/16.
 */
public abstract class BaseCharacteristic {

        //Param: UINT16 in seconds with resolution 1 second.


    protected BluetoothGattCharacteristic characteristic;

    /**
     * Create a new BluetoothGattCharacteristic.
     * <p>Requires {@link Manifest.permission#BLUETOOTH} permission.
     *
     * @param uuid        The UUID for this characteristic
     * @param properties  Properties of this characteristic
     * @param permissions Permissions for this characteristic
     */
    public BaseCharacteristic(UUID uuid, int properties, int permissions, BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public BaseCharacteristic() {

    }

    public String getUuidString() {
        if (characteristic != null) return characteristic.getUuid().toString();
        else return "";
    }

    public abstract boolean elabData();

    public BluetoothGattCharacteristic getCharacteristic() {
        return this.characteristic;
    }

    public void setCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }
}
