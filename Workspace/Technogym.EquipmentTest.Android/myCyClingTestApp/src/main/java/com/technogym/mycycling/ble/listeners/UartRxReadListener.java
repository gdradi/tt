package com.technogym.mycycling.ble.listeners;

import com.technogym.mycycling.listeners.IListener;

/***
 * Interface related to the UartRxReadListener notificable entities
 *
 * @author Federico Foschini
 */
public interface UartRxReadListener extends IListener {

    /***
     * Method to notify the read complete event
     * @param data: result of the read operation
     * @param tag: tag related to the write operation context
     */
    public void onReadComplete(String data, String tag);
}
