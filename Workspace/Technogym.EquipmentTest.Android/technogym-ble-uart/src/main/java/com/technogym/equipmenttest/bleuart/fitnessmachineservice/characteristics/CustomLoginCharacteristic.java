package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;

/**
 * Created by amarozzi on 22/03/2019.
 */

public class CustomLoginCharacteristic extends BaseCharacteristic {

    /**
     * @param characteristic
     */
    public CustomLoginCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    @Override
    public boolean elabData() {
        return true;
    }

}