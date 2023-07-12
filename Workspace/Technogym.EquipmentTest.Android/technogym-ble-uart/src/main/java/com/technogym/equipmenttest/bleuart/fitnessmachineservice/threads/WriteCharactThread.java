package com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;

import java.util.concurrent.ExecutorService;

/**
 * Created by developer on 04/01/16.
 */
public class WriteCharactThread implements Runnable {

    private BluetoothGatt mGatt;
    private BluetoothGattCharacteristic mCharacteristic;

    public static void send(ExecutorService service, BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        service.execute(new WriteCharactThread(gatt, characteristic));
    }

    WriteCharactThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        mGatt = gatt;
        mCharacteristic = characteristic;
    }

    @Override
    public void run() {
        if (mGatt != null) {
            //ConstantsFitnessMachine.LOCK.acquireUninterruptibly();
            int retry = 0;
            Log.d(ConstantsFitnessMachine.TAG, "Init write characteristic");
            while (!mGatt.writeCharacteristic(mCharacteristic) && retry < ConstantsFitnessMachine.MAX_RETRY) {
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
