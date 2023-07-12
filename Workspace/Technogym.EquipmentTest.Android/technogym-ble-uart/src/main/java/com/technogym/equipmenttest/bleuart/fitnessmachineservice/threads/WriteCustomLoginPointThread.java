package com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;

/**
 * Created by developer on 04/01/16.
 */
public class WriteCustomLoginPointThread implements Runnable {

    private BluetoothGatt mGatt;
    private BluetoothGattCharacteristic mCharacteristic;
    private String userId;

    public static void send(ExecutorService service, BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, String userId) {
        service.execute(new WriteCustomLoginPointThread(gatt, characteristic, userId));
    }

    WriteCustomLoginPointThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, String userId) {
        this.mGatt = gatt;
        this.mCharacteristic = characteristic;
        this.mCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        this.userId = userId;
    }

    public void run() {
        if (this.userId == null) {
            Log.e(ConstantsFitnessMachine.TAG, "Error: no userId to write");
        }

        try {
            int retry = 0;
            mCharacteristic.setValue(userId.getBytes(StandardCharsets.UTF_8));
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
