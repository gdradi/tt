package com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.CustomHrMeasurement;

import java.util.concurrent.ExecutorService;

/**
 * Created by developer on 04/01/16.
 */
public class WriteCustomHrMeasurementThread implements Runnable {

    private BluetoothGatt mGatt;
    private BluetoothGattCharacteristic mCharacteristic;
    private CustomHrMeasurement mCmd;

    public static void send(ExecutorService service, BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, CustomHrMeasurement cmd) {
        service.execute(new WriteCustomHrMeasurementThread(gatt, characteristic, cmd));
    }

    WriteCustomHrMeasurementThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, CustomHrMeasurement cmd) {
        this.mGatt = gatt;
        this.mCharacteristic = characteristic;
        this.mCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        this.mCmd = cmd;
    }

    public void run() {
        if (this.mCmd == null) {
            Log.e(ConstantsFitnessMachine.TAG, "Error: no CustomHrMeasurement to write");
        }

        try {
            int retry = 0;
            mCharacteristic.setValue(mCmd.encode());
            while(!this.mGatt.writeCharacteristic(this.mCharacteristic) && retry < ConstantsFitnessMachine.MAX_RETRY) {
                Log.d("technogymbtleuart", "Retry to write character for cmd!");
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException var8) {
                    var8.printStackTrace();
                }
                Log.d(ConstantsFitnessMachine.TAG, "retry = " + retry);
                retry++;
            }
            Log.d(ConstantsFitnessMachine.TAG, "WriteCustomHrMeasurement success");
        } catch (Exception exception) {
            Log.d(ConstantsFitnessMachine.TAG, "Error: " + exception.getMessage());
        }

    }
}
