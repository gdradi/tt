package com.technogym.equipmenttest.bleuart.technogymbtleuart.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;


/**
 * Created by sventurini on 20/07/16.
 */
public class UartTxCharacteristic extends BaseCharacteristic {

    public UartTxCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    private boolean mWriting = false;

    @Override
    public boolean elabData() {
        return false;
    }
}
