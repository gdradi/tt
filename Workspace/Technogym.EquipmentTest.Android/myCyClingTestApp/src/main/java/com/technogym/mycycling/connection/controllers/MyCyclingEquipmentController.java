package com.technogym.mycycling.connection.controllers;

import android.content.Context;
import android.util.Log;

import com.technogym.mycycling.ble.controllers.BleController;
import com.technogym.mycycling.ble.controllers.IBleController;
import com.technogym.mycycling.ble.listeners.IBleControllerListener;
import com.technogym.mycycling.ble.models.BleDevice;
import com.technogym.mycycling.ble.models.BleUUIDs;
import com.technogym.mycycling.commands.models.ICommand;
import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.commands.commands.SerialNumberCommand;
import com.technogym.mycycling.connection.listeners.EquipmentConnectionListener;
import com.technogym.mycycling.connection.models.EquipmentConnectionState;
import com.technogym.mycycling.dfu.DfuService;
import com.technogym.mycycling.exceptions.ConnectionException;

import no.nordicsemi.android.dfu.DfuProgressListener;
import no.nordicsemi.android.dfu.DfuServiceInitiator;
import no.nordicsemi.android.dfu.DfuServiceListenerHelper;

public class MyCyclingEquipmentController extends EquipmentController
        implements IBleControllerListener, DfuProgressListener {

    protected IBleController mBleController;
    protected Context mContext;

    // { Construction

    protected MyCyclingEquipmentController(Context context, EquipmentConnectionState initialState) {
        super(initialState);
        mContext = context;
        mBleController = new BleController(context, this);
    }

    // }

    // { Public methods

    /***
     * Gets an instance of this controller
     * @param context: context
     * @param initialState: initial state of the equipment
     * @return an instance of this controller
     */
    public static MyCyclingEquipmentController create(Context context, EquipmentConnectionState initialState) {
        return (new MyCyclingEquipmentController(context, initialState));
    }

    // }

    // { EquipmentController implementation

    @Override
    public void searchDevice(String deviceName) {
        mBleController.searchDevice(deviceName);
    }

    @Override
    public boolean connect(String deviceAddress) {
        return mBleController.connect(deviceAddress);
    }

    @Override
    public void reconnect() { mBleController.reconnect(); }

    @Override
    public void disconnect() {
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
    }

    @Override
    public boolean isBleConnected() { return mBleController.isBleConnected(); }

    @Override
    public BleDevice getBleDevice(String deviceName) { return mBleController.getBleDevice(deviceName); }

    @Override
    public void toggleBleConnection() { mBleController.toggleBleConnection(); }

    @Override
    public void sendCommand(ICommand msgCommand) {
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
    }

    @Override
    public void onTargetDeviceFound(String data) {
        for(EquipmentConnectionListener l : getRegisteredListeners()) {
            l.onTargetDeviceFound(data);
        }
    }

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

    @Override
    public void upgradeEquipmentFirmware(String fwPath) {
        mBleController.disconnect();
        try {
            Thread.sleep(800);
            mBleController.close();
            Thread.sleep(500);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "upgrade disconnect sleep error: " + ex.getMessage());
        }

        // PROCEDURE DI TEST RELATIVE ALL'UPGRADE (TEST PER SIMULARE L'UPGRADE) ------------------
        /*
        try {
            Thread.sleep(2500);
            mBleController.reconnect();
            Thread.sleep(2500);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "upgrade error: " + ex.getMessage());
        }
        notifyAboutUpgradeCompleted(true);
        */
        // ---------------------------------------------------------------------------------------

        // PROCEDURE VALIDE E CORRETTE RELATIVE ALL'UPGRADE --------------------------------------
        DfuServiceListenerHelper.registerProgressListener(mContext, this);

        DfuServiceInitiator dfuServiceInitiator =
                new DfuServiceInitiator(mBleController.getConnectedBleDevice().getDevice().getAddress())
                    .setDeviceName(mBleController.getConnectedBleDevice().getDevice().getName())
                    .setKeepBond(false)
                    .setZip(fwPath);
        dfuServiceInitiator.start(mContext, DfuService.class);
        // ---------------------------------------------------------------------------------------
    }

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

    // { DfuProgressListener implementation

    @Override
    public void onDeviceConnecting(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDeviceConnecting - device: " + deviceAddress);
    }

    @Override
    public void onDeviceConnected(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDeviceConnected - device: " + deviceAddress);
    }

    @Override
    public void onDfuProcessStarting(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDfuProcessStarting - device: " + deviceAddress);
    }

    @Override
    public void onDfuProcessStarted(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDfuProcessStarted - device: " + deviceAddress);
    }

    @Override
    public void onEnablingDfuMode(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onEnablingDfuMode - device: " + deviceAddress);
    }

    @Override
    public void onProgressChanged(String deviceAddress, int percent, float speed, float avgSpeed, int currentPart, int partsTotal) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - device: " + deviceAddress);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - percent: " + percent);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - speed: " + speed);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - avgSpeed: " + avgSpeed);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - currentPart: " + currentPart);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onProgressChanged - partsTotal: " + partsTotal);
    }

    @Override
    public void onFirmwareValidating(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onFirmwareValidating - device: " + deviceAddress);
    }

    @Override
    public void onDeviceDisconnecting(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDeviceDisconnecting - device: " + deviceAddress);
    }

    @Override
    public void onDeviceDisconnected(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDeviceDisconnected - device: " + deviceAddress);
    }

    @Override
    public void onDfuCompleted(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDfuCompleted - device: " + deviceAddress);
        DfuServiceListenerHelper.unregisterProgressListener(mContext, this);

        String targetAddr = mBleController.getConnectedBleDevice().getDevice().getAddress();
        mBleController = new BleController(mContext, this);
        mBleController.connect(targetAddr);
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {
            Log.e(this.getClass().getSimpleName(), "upgrade error: " + ex.getMessage());
        }
        notifyAboutUpgradeCompleted(true);
    }

    @Override
    public void onDfuAborted(String deviceAddress) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onDfuAborted - device: " + deviceAddress);
        notifyAboutUpgradeCompleted(false);
    }

    @Override
    public void onError(String deviceAddress, int error, int errorType, String message) {
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onError - device: " + deviceAddress);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onError - error: " + error);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onError - errorType: " + errorType);
        Log.i(this.getClass().getSimpleName(), "[Dfu Progress] onError - message: " + message);
        notifyAboutUpgradeCompleted(false);
    }

    // }
}
