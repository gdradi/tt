package com.technogym.mycycling.ble.threads;

import android.util.Log;

import com.technogym.mycycling.ble.listeners.UartRxReadListener;
import com.technogym.mycycling.commands.models.CommandConstants;
import com.technogym.mycycling.support.ConversionHelper;

import java.util.concurrent.Semaphore;

/***
 * Uart RX Read Handler
 *
 * @author Federico Foschini
 */
public class UartRxReader implements Runnable {

    // { Internal fields

    private String mTag;
    private UartRxReadListener mNotificable;
    private String riddenData;
    private Semaphore lockSem;
    private boolean completed;

    // }

    // { Constructors

    public UartRxReader(UartRxReadListener uartRxListener, String tag) {
        mNotificable = uartRxListener;
        mTag = tag;
        riddenData = "";
        lockSem = new Semaphore(0);
        completed = false;
    }

    // }

    // { Public methods

    /***
     * Method to add ridden data
     * @param data: ridden data
     */
    public synchronized void readData(byte[] data) {
        //lockSem.release();
        String curData = ConversionHelper.BytesToString(data);
        riddenData = riddenData + curData;
    }

    /***
     * Force the thread stop
     */
    public synchronized void forceEnd() {
        //lockSem.release();
        completed = true;
    }

    // }

    // { Runnable implementation

    @Override
    public void run() {
        Log.i(this.getClass().getSimpleName(), "Start Read for operation: " + mTag);
        while(!completed) {
            //try { lockSem.acquire(); }
            //catch (InterruptedException e) { e.printStackTrace(); }

            // check reply end
            if(riddenData.contains(CommandConstants.END_COMMAND_REPLY_TOKEN)) {
                Log.i(this.getClass().getSimpleName(), "Completed Read for operation: " + mTag);
                completed = true;
            }
        }
        mNotificable.onReadComplete(riddenData, mTag);
    }

    // }
}
