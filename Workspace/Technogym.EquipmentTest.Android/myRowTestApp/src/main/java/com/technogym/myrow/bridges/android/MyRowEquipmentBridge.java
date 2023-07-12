package com.technogym.myrow.bridges.android;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.technogym.myrow.connection.controllers.IEquipmentController;
import com.technogym.myrow.managers.MyRowTestActionManager;


public class MyRowEquipmentBridge extends EquipmentBridge<MyRowTestActionManager> implements IMyRowEquipmentBridge {

    protected IEquipmentController mEquipmentController;

    // { Construction

    protected MyRowEquipmentBridge(final Activity activity,
                                   final IEquipmentController equipmentController,
                                   final MyRowTestActionManager actionManager) {
        super(activity, actionManager);
        mEquipmentController = equipmentController;

        //mListener = listener;
        //mBluetoothController = bluetoothController;
        //mSystemProxy = SystemProxy.getInstance(null);
    }

    @JavascriptInterface
    public static MyRowEquipmentBridge create(final Activity activity,
                                              final IEquipmentController equipmentController,
                                              final MyRowTestActionManager actionManager) {
        return new MyRowEquipmentBridge(activity, equipmentController, actionManager);
    }

    @Override
    public boolean isUnity() {
        return false;
    }

    @Override
    public boolean isMyRun() {
        return false;
    }

    @Override
    public boolean isMyCycling() {
        return false;
    }

    @Override
    public boolean isMyRow() {
        return true;
    }

    @Override
    public void askForEquipmentSerialNumber() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void setEquipmentSerialNumber(String serialNumber) {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void scanBarCode() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public boolean isBluetoothConnected() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void toggleBluetooth() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void askForEquipmentFirmwareVersion() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void askForBootloaderFirmwareVersion() {

    }

    @Override
    public boolean isBleConnected() {
        return false;
    }

    @Override
    public void upgradeEquipmentFirmware(String firmwareFilePath) {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void startBootloaderMode() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void reconnectBle() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void disconnectEquipment() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    @Override
    public void askForUpdatedFirmwareVersion() {
        throw new UnsupportedOperationException("Not implemented yed");
    }

    // }

    // { IMyCyclingEquipmentBridge abstract methods implementation


}
