package com.technogym.mycycling.ble.threads;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.mycycling.ble.listeners.UartTxWriterListener;
import com.technogym.mycycling.exceptions.ConnectionException;

import java.util.Arrays;
import java.util.concurrent.Semaphore;

/***
 * Uart TX Write Handler
 *
 * @author Federico Foschini
 */
public class UartTxWriter implements Runnable {

    // { Internal fields

    private String mTag;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattCharacteristic mCharacteristic;
    private UartTxWriterListener mNotificable;
    private byte[] mData;
    private int curWriteStep;
    private Semaphore lockSem;

    // }

    // { Constructors

    public UartTxWriter(BluetoothGatt bluetoothGatt, UartTxWriterListener notificable,
                        BluetoothGattCharacteristic characteristic, String tag, byte[] dataToWrite) {
        mBluetoothGatt = bluetoothGatt;
        mNotificable = notificable;
        mCharacteristic = characteristic;
        mTag = tag;
        mData = dataToWrite;

        lockSem = new Semaphore(0);
        curWriteStep = 0;
    }

    // }

    // { Public methods

    /***
     * Method to unlock the write operation
     */
    public synchronized void goWrite() {
        lockSem.release();
    }

    // }

    // { Runnable implementation

    @Override
    public void run() {
        curWriteStep = 0;
        Log.i(this.getClass().getSimpleName(),
                "Write Data: " + Arrays.toString(mData) + " - " + mData.length);

        boolean res = true;
        for(int i = 0; i < mData.length; i++) {

            Log.i(this.getClass().getSimpleName(), "Characteristic Property: " + mCharacteristic.getProperties());

            mCharacteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            int curVal = mData[i];
            Log.i(this.getClass().getSimpleName(), "Writing Data: " + curVal);
            //Log.i(this.getClass().getSimpleName(), "Sending int data: " + curVal);
            boolean setValRes = mCharacteristic.setValue(curVal, BluetoothGattCharacteristic.FORMAT_UINT8, 0);
            Log.i(this.getClass().getSimpleName(), "Set Value Data Result: " + setValRes);
            boolean status = mBluetoothGatt.writeCharacteristic(mCharacteristic);
            Log.i(this.getClass().getSimpleName(), "Write Data to Characteristic Result: " + status);
            res = res && status;

            if(!res) {
                break;
            }
            try {
                lockSem.acquire();
            } catch (Exception e) {
                res = false;
                Log.e(this.getClass().getSimpleName(), "Semaphore Acquire Error: " + e.getMessage());
                break;
            }
        }
        mNotificable.onWriteCompleted(res, mTag);
    }

    // }
}
