package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;


/**
 * Created by sventurini on 16/02/2017.
 */

public class TotalLengthDataCharacteristic extends BaseCharacteristic {

    private int totalLengthData = 0; //cm 0 - 255cm

    public TotalLengthDataCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    public TotalLengthDataCharacteristic() {
        super();
    }

    public int getTotalLengthData() {
        return totalLengthData;
    }

    public void setTotalLengthData(int totalLengthData) {
        this.totalLengthData = totalLengthData;
    }

    @Override
    public boolean elabData() {
        totalLengthData = getCharacteristic().getIntValue(BluetoothGattCharacteristic.FORMAT_UINT8, 0);
        Log.d(ConstantsFitnessMachine.TAG + "_S", "Total length: " + totalLengthData + "cm");
        return true;
    }
}
