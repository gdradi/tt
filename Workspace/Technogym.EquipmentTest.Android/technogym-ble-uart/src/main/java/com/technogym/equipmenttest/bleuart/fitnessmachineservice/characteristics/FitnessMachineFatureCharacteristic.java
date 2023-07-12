package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;


/**
 * Created by sventurini on 13/01/2017.
 */

public class FitnessMachineFatureCharacteristic extends BaseCharacteristic {

    public FitnessMachineFatureCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    @Override
    public boolean elabData() {
        return true;
    }
}
