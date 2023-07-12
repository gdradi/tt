package com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;

import java.util.concurrent.ExecutorService;

/**
 * Created by sventurini on 05/05/16.
 */
public class ReadCharacteristicThread implements Runnable {

    private BluetoothGatt mGatt;
    private BluetoothGattCharacteristic mCharacteristic;

    public static void send(ExecutorService service, BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        service.execute(new ReadCharacteristicThread(gatt, characteristic));
    }

    ReadCharacteristicThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        mGatt = gatt;
        mCharacteristic = characteristic;

    }

    @Override
    public void run() {
        if (mGatt != null) {
            //ConstantsFitnessMachine.LOCK.acquireUninterruptibly();
            int retry = 0;
            while (!mGatt.readCharacteristic(mCharacteristic) && retry < ConstantsFitnessMachine.MAX_RETRY) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(ConstantsFitnessMachine.TAG, "retry = " + retry);
                retry++;
            }
        }
    }
}
