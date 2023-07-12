package com.technogym.equipmenttest.bleuart.technogymbtleuart.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.technogymbtleuart.BLEUartConstants;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.Exceptions.ExceptionCommandUart;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandModel;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by developer on 04/01/16.
 */
public class WriteUartTxCharactThread implements Runnable {

    private static final int TIMEOUT = 10000;

    private BluetoothGatt mGatt;
    private BluetoothGattCharacteristic mCharacteristic;
    private CommandModel mCmd;
    public static boolean uartCharWrited = false;

    public static void send(ExecutorService service, BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, CommandModel cmd) {
        try {
            service.execute(new WriteUartTxCharactThread(gatt, characteristic, cmd));

        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    WriteUartTxCharactThread(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, CommandModel cmd) {
        mGatt = gatt;
        mCharacteristic = characteristic;
        mCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
        mCmd = cmd;
    }

    @Override
    public void run() {
        if (mCmd == null) Log.d(BLEUartConstants.TAG, "Error: no command to write");

        try {
            String rawCommand = mCmd.encode();
            for (byte character : rawCommand.getBytes()) {
                byte[] value = new byte[1];
                value[0] = character;
                mCharacteristic.setValue(value);
                if (mGatt != null) {
                    // try {
                    //if (BTLEConstants.LOCK.tryAcquire(TIMEOUT, TimeUnit.MILLISECONDS)) {
                    if (!uartCharWrited) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        while (!mGatt.writeCharacteristic(mCharacteristic)) {
                            Log.d(BLEUartConstants.TAG, "Retry to write character for cmd!");
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    //} else {
                    //    BTLEConstants.LOCK.release();
                    //    Log.d(BLEUartConstants.TAG, "Timeout acquire write uart characteristic");
                    //}
                    // } catch (InterruptedException e) {
                    //     e.printStackTrace();
                    //     BTLEConstants.LOCK.release();
                    //     Log.d(BLEUartConstants.TAG, "Timeout acquire write uart characteristic");
                    // }
                }
            }
            Log.d("st_debug_raw", "write command to TX Characteristic = " + mCmd.getCmd());

        } catch (ExceptionCommandUart exceptionCommandUart) {
            exceptionCommandUart.printStackTrace();
            Log.d(BLEUartConstants.TAG, "Error: " + exceptionCommandUart.getMessage());

        }
    }
}
