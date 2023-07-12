package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.Manifest;
import android.bluetooth.BluetoothGattCharacteristic;


import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;

import java.io.UnsupportedEncodingException;

/**
 * Created by sventurini on 29/01/16.
 */
public class StringUtf8Characteristic extends BaseCharacteristic {

    private final static String UTF8 = "UTF-8";
    private String value = "";
    /**
     * Create a new BluetoothGattCharacteristic.
     * <p>Requires {@link Manifest.permission#BLUETOOTH} permission.
     *
     * @param characteristic
     */
    public StringUtf8Characteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    /**
     * @return
     */
    public String getValue() {
        return value;
    }

    @Override
    public boolean elabData() {
        byte[] valueByte = getCharacteristic().getValue();
        if (valueByte == null) this.value = "";
        try {
            this.value = new String(valueByte, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            this.value = "";
        }
        return true;
    }
}
