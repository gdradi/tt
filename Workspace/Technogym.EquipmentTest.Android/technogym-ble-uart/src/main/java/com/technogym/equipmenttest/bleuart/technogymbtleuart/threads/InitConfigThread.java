package com.technogym.equipmenttest.bleuart.technogymbtleuart.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.btlesupport.BTLEConstants;
import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.BLEUartConstants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sventurini on 05/05/15.
 */
public class InitConfigThread implements Runnable {

    private static final int TIMEOUT = 10000;

    private BaseCharacteristic mCharacteristic;
    private BluetoothGatt mGatt;

    public InitConfigThread(BluetoothGatt gatt, BaseCharacteristic characteristic) {
        try {
            BTLEConstants.LOCK.release();
            Log.d(BLEUartConstants.TAG, "Unlock Sem aphore before init config thread");

        } catch (Exception e) {
            e.printStackTrace();

        }

        mGatt = gatt;
        mCharacteristic = characteristic;
    }

    public static void execute(ExecutorService service, BluetoothGatt gatt, BaseCharacteristic characteristic) {
        service.execute(new InitConfigThread(gatt, characteristic));
    }

    public void run() {
        if (mGatt != null && mCharacteristic != null) {
            try {
                if (BTLEConstants.LOCK.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS)) {
                    mGatt.setCharacteristicNotification(mCharacteristic.getCharacteristic(), true);

                    //Enabled remote notifications
                    BluetoothGattDescriptor descriptor = mCharacteristic.getCharacteristic().getDescriptor(BTLEConstants.BaseCharacteristics.DESCRIPTOR_CONFIGURATION_UUID);
                    if (descriptor != null) {
                        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                        mGatt.writeDescriptor(descriptor);
                        Log.d(BLEUartConstants.TAG, "Init wrote enable indicate for UartRX");

                    } else {
                        Log.d(BLEUartConstants.TAG, "Timeout acquire wrote enable indicate for UartRX");

                    }
                    BTLEConstants.LOCK.release();


                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                BTLEConstants.LOCK.release();
                Log.d(BLEUartConstants.TAG, "Timeout acquire wrote enable indicate for UartRX");
            }
        }
    }
}
