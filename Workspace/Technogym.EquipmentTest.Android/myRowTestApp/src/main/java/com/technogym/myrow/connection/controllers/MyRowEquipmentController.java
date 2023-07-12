package com.technogym.myrow.connection.controllers;

import android.content.Context;
import android.util.Log;

import com.technogym.myrow.commands.models.ICommand;
import com.technogym.myrow.commands.models.ICommandReply;
import com.technogym.myrow.connection.listeners.EquipmentConnectionListener;
import com.technogym.myrow.connection.models.EquipmentConnectionState;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

public class MyRowEquipmentController extends EquipmentController {


    protected Context mContext;

    // { Construction

    protected MyRowEquipmentController(Context context, EquipmentConnectionState initialState) {
        super(initialState);
        mContext = context;
    }

    // }

    // { Public methods

    /***
     * Gets an instance of this controller
     * @param context: context
     * @param initialState: initial state of the equipment
     * @return an instance of this controller
     */
    public static MyRowEquipmentController create(Context context, EquipmentConnectionState initialState) {
        return (new MyRowEquipmentController(context, initialState));
    }

    // }

    // { EquipmentController implementation

    @Override
    public void searchDevice(String deviceName) {
        //mBleController.searchDevice(deviceName);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean connect(String deviceAddress) {
        //return mBleController.connect(deviceAddress);
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void reconnect() {
        //mBleController.reconnect();
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void disconnect() {
        /*
        mBleController.disconnect();

        try {
            Thread.sleep(800);
            mBleController.close();
            Thread.sleep(500);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "disconnect sleep error: " + ex.getMessage());
        }

        mBleController.close();
        mBleController = new BleController(mContext, this);
        */
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public boolean isBleConnected() {
        //return mBleController.isBleConnected();
        throw new UnsupportedOperationException("Not implemented yet");
    }

    //@Override
    //public BleDevice getBleDevice(String deviceName) { return mBleController.getBleDevice(deviceName); }

    @Override
    public void toggleBleConnection() {
        //mBleController.toggleBleConnection();
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void sendCommand(ICommand msgCommand) {
        /*
        if(!msgCommand.isEquipmentInfoCommand()) {
            mBleController.writeBleData(BleUUIDs.UUID_TX_SERVICE, msgCommand.getName(), msgCommand.serializeAsString());
        } else {
            // TODO : manage characteristic read

            switch (msgCommand.getName()) {
                case SerialNumberCommand.CMD_NAME:
                    mBleController.readCharacteristic(BleUUIDs.UUID_SERIAL_NUMBER_CHAR, msgCommand.getName());
                    break;
            }
        }
        */
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public void onTargetDeviceFound(String data) {
        for(EquipmentConnectionListener l : getRegisteredListeners()) {
            l.onTargetDeviceFound(data);
        }
    }

    /*
    @Override
    public void onConnectionStateChange(int state) {
        setConnectionState(EquipmentConnectionState.values()[state]);
        notifyAboutConnectionStateChanged(getConnectionState());
    }

    @Override
    public void onValueReceived(String tag, ICommandReply reply) {
        Log.i(this.getClass().getSimpleName(), "on value received for tag: " + tag + ": " + reply.getName());
        Log.i(this.getClass().getSimpleName(), "listeners count: " + getRegisteredListeners().size());
        for(EquipmentConnectionListener l : getRegisteredListeners()) {
            l.onValueReceived(reply);
        }
    }
    */

    // }

    // { IBleControllerListener implementation

    // }

    // { Private methods

    protected void notifyAboutConnectionStateChanged(EquipmentConnectionState state) {
        for(EquipmentConnectionListener l : getRegisteredListeners()) {
            l.onConnectionStateChanged(state);
        }
    }

    protected void notifyAboutUpgradeCompleted(boolean upgradeResult) {
        for(EquipmentConnectionListener l : getRegisteredListeners()) {
            l.onUpgradeDone(upgradeResult);
        }
    }

    // }

}
