package com.technogym.mycycling.ble.listeners;

import com.technogym.mycycling.listeners.IListener;

/***
 * Interface related to the UartTxWriter notificable entities
 *
 * @author Federico Foschini
 */
public interface UartTxWriterListener extends IListener {

    /***
     * Method to notify the write complete event
     * @param writeResult: result of the write operation
     * @param tag: tag related to the write operation context
     */
    public void onWriteCompleted(boolean writeResult, String tag);
}
