package com.technogym.mycycling.ble.listeners;

import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.listeners.IListener;

/***
 * Method to notify the Ble Controller's events
 *
 * @author Federico Foschini
 */
public interface IBleControllerListener extends IListener {

    /***
     * Method to notify the device found event
     * @param data: parameter data to notify
     */
    public void onTargetDeviceFound(String data);

    /***
     * Method to notify the connection's state change
     * @param state: new connection's state
     */
    public void onConnectionStateChange(int state);

    /**
     * Method to notify the value reception event
     * @param tag: ridden characteristic operation tag
     * @param reply: reply of the read characteristic
     */
    public void onValueReceived(String tag, ICommandReply reply);
}
