package com.technogym.equipmenttest.bleuart.fitnessmachineservice.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattDescriptor;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;

import java.util.concurrent.ExecutorService;

/**
 * Created by developer on 04/01/16.
 */
public class WriteDescriptorThread implements Runnable {

    private BluetoothGatt mGatt;
    private BaseCharacteristic mCharacteristic;

    public static void enableNotificationValue(ExecutorService service, BluetoothGatt gatt, BaseCharacteristic characteristic) {
        service.execute(new WriteDescriptorThread(gatt, characteristic));
    }

    WriteDescriptorThread(BluetoothGatt gatt, BaseCharacteristic characteristic) {
        mGatt = gatt;
        mCharacteristic = characteristic;
    }

    @Override
    public void run() {
        if (mGatt != null) {
            //ConstantsFitnessMachine.LOCK.acquireUninterruptibly();
            Log.d("Lock", "acquire");
            if (mCharacteristic == null || mCharacteristic.getCharacteristic() == null) return;
            mGatt.setCharacteristicNotification(mCharacteristic.getCharacteristic(), true);
            //Enabled remote notifications

            BluetoothGattDescriptor descriptor = mCharacteristic.getCharacteristic().getDescriptor(ConstantsFitnessMachine.Characteristics.DESCRIPTOR_CONFIGURATION_UUID);
            if (descriptor == null) {
                Log.d("Lock", "release");
                return;
            }

            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            int retry = 0;
            Log.d(ConstantsFitnessMachine.TAG, "Init wrote enable notify");
            while (!mGatt.writeDescriptor(descriptor) && retry < ConstantsFitnessMachine.MAX_RETRY) {
                try {
                    Thread.sleep(600);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d(ConstantsFitnessMachine.TAG, "retry = " + retry);
                retry++;
            }
        }
    }
}
