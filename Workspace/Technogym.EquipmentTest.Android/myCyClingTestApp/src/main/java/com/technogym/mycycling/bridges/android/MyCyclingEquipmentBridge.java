package com.technogym.mycycling.bridges.android;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.google.zxing.integration.android.IntentIntegrator;
import com.technogym.mycycling.commands.commands.BootloaderFirmwareVersionCommand;
import com.technogym.mycycling.commands.commands.UpdatedFirmwareVersionCommand;
import com.technogym.mycycling.commands.factories.CommandFactory;
import com.technogym.mycycling.commands.commands.FirmwareVersionCommand;
import com.technogym.mycycling.commands.commands.SerialNumberCommand;
import com.technogym.mycycling.commands.commands.StartingBootloaderModeCommand;
import com.technogym.mycycling.connection.controllers.IEquipmentController;
import com.technogym.mycycling.exceptions.ConnectionException;
import com.technogym.mycycling.managers.MyCyclingTestActionManager;

import it.spot.android.logger.domain.Logger;

public class MyCyclingEquipmentBridge extends EquipmentBridge<MyCyclingTestActionManager> implements IMyCyclingEquipmentBridge {

    protected IEquipmentController mEquipmentController;

    // { Construction

    protected MyCyclingEquipmentBridge(final Activity activity,
                                       final IEquipmentController equipmentController,
                                       final MyCyclingTestActionManager actionManager) {
        super(activity, actionManager);
        mEquipmentController = equipmentController;

        //mListener = listener;
        //mBluetoothController = bluetoothController;
        //mSystemProxy = SystemProxy.getInstance(null);
    }

    @JavascriptInterface
    public static MyCyclingEquipmentBridge create(final Activity activity,
                                                  final IEquipmentController equipmentController,
                                                  final MyCyclingTestActionManager actionManager) {
        return new MyCyclingEquipmentBridge(activity, equipmentController, actionManager);
    }

    // }

    // { IMyCyclingEquipmentBridge abstract methods implementation

    @Override
    @JavascriptInterface
    public boolean isBluetoothConnected() {
        return false;
    }

    @Override
    @JavascriptInterface
    public void askForEquipmentFirmwareVersion() {
        Log.i(this.getClass().getSimpleName(), "asking for Equipment Firmware Version...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "asking for Equipment Firmware Version...");
        try {
            mEquipmentController.sendCommand(CommandFactory.create(FirmwareVersionCommand.CMD_NAME, null));
        } catch (ConnectionException e) {
            Log.e(this.getClass().getSimpleName(), "askForEquipmentFirmwareVersion error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "askForEquipmentFirmwareVersion error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @JavascriptInterface
    public void askForBootloaderFirmwareVersion() {
        Log.i(this.getClass().getSimpleName(), "asking for Equipment Bootloader Firmware Version...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "asking for Equipment Bootloader Firmware Version...");
        try {
            mEquipmentController.sendCommand(CommandFactory.create(BootloaderFirmwareVersionCommand.CMD_NAME, null));
        } catch (ConnectionException e) {
            Log.e(this.getClass().getSimpleName(), "askForBootloaderFirmwareVersion error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "askForBootloaderFirmwareVersion error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @JavascriptInterface
    public boolean isUnity() { return false; }

    @Override
    @JavascriptInterface
    public boolean isMyRun() { return false; }

    @Override
    @JavascriptInterface
    public boolean isMyCycling() { return true; }

    @Override
    @JavascriptInterface
    public boolean isBleConnected() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "checking if ble equipment is connected...");
        boolean isConnected = mEquipmentController.isBleConnected();
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "ble equipment is connected: " + isConnected);
        return isConnected;
    }

    @Override
    @JavascriptInterface
    public void toggleBluetooth() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "toggle ble connection");
        mEquipmentController.toggleBleConnection();
    }

    @Override
    @JavascriptInterface
    public void askForEquipmentSerialNumber() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "asking for Equipment Serial Number...");
        try {
            mEquipmentController.sendCommand(CommandFactory.create(SerialNumberCommand.CMD_NAME, null));
        } catch (ConnectionException e) {
            Log.e(this.getClass().getSimpleName(), "askForEquipmentSerialNumber error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "askForEquipmentSerialNumber error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @JavascriptInterface
    public void setEquipmentSerialNumber(final String serialNumber) { }

    @Override
    @JavascriptInterface
    public void upgradeEquipmentFirmware(String firmwareFilePath) {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Upgrading Equipment Firmware...");
        Log.i(this.getClass().getSimpleName(), "Upgrading Equipment Firmware...");
        mEquipmentController.upgradeEquipmentFirmware(firmwareFilePath);
    }

    @Override
    @JavascriptInterface
    public void scanBarCode() {
        IntentIntegrator integrator = new IntentIntegrator(this.mActivity);
        integrator.setOrientationLocked(true);
        integrator.initiateScan();

        /*
            OLD Android Version

            IntentIntegrator integrator = new IntentIntegrator(this.mActivity);
            integrator.addExtra("SCAN_WIDTH", 800);
            integrator.addExtra("SCAN_HEIGHT", 200);
            integrator.addExtra("RESULT_DISPLAY_DURATION_MS", 3000L);
            integrator.addExtra("PROMPT_MESSAGE", "Custom prompt to scan a product");
            integrator.initiateScan(IntentIntegrator.PRODUCT_CODE_TYPES);
         */
    }

    @Override
    @JavascriptInterface
    public void startBootloaderMode() {
        Log.i(this.getClass().getSimpleName(), "Starting Bootloader Mode...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Starting Bootloader Mode...");
        try {
            mEquipmentController.sendCommand(CommandFactory.create(StartingBootloaderModeCommand.CMD_NAME, null));
        } catch (ConnectionException e) {
            Log.e(this.getClass().getSimpleName(), "startBootloaderMode error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "startBootloaderMode error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    @JavascriptInterface
    public void reconnectBle() {
        Log.i(this.getClass().getSimpleName(), "Reconnecting BLE...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Reconnecting BLE...");
        mEquipmentController.reconnect();
        Log.i(this.getClass().getSimpleName(), "BLE reconnected");
    }

    @Override
    @JavascriptInterface
    public void disconnectEquipment() {
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "Disconnecting BLE...");
        mEquipmentController.disconnect();
    }

    @Override
    @JavascriptInterface
    public void askForUpdatedFirmwareVersion() {
        Log.i(this.getClass().getSimpleName(), "asking for Equipment Updated Firmware Version...");
        Logger.getInstance().logDebug(this.getClass().getSimpleName(), "asking for Equipment Updated Firmware Version...");
        try {
            mEquipmentController.sendCommand(CommandFactory.create(FirmwareVersionCommand.CMD_NAME, null));
        } catch (ConnectionException e) {
            Log.e(this.getClass().getSimpleName(), "askForUpdatedFirmwareVersion error: " + e.getMessage());
            Logger.getInstance().logError(this.getClass().getSimpleName(), "askForUpdatedFirmwareVersion error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
