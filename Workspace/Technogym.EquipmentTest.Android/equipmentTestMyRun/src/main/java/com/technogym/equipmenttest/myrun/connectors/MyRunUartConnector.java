package com.technogym.equipmenttest.myrun.connectors;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.technogym.android.myrun.sdk.bluetooth.dispatchers.BluetoothConnectionNotifier;
import com.technogym.android.myrun.sdk.bluetooth.dispatchers.IBluetoothConnectionNotifier;
import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.connection.controllers.EquipmentController;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;
import com.technogym.android.myrun.sdk.system.listeners.ISystemListener;
import com.technogym.sdk.fitnessmachineservice.FitnessMachineManager;
import com.technogym.sdk.fitnessmachineservice.model.FitnessBtDevice;
import com.technogym.sdk.technogymbtleuart.BLEUartConstants;
import com.technogym.sdk.technogymbtleuart.Exceptions.ExceptionCommandUart;
import com.technogym.sdk.technogymbtleuart.characteristics.UartRxCharacteristic;
import com.technogym.sdk.technogymbtleuart.model.CommandModel;
import com.technogym.sdk.technogymbtleuart.model.CommandType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import it.spot.android.logger.domain.ILogger;
import it.spot.android.logger.domain.Logger;

public class MyRunUartConnector extends EquipmentController implements IEquipmentController, IBleController {

    private static final String TAG = MyRunUartConnector.class.getSimpleName();
    private final FitnessMachineManager manager;
    private final List<BluetoothDevice> devices = new ArrayList<>();
    private final IBluetoothConnectionNotifier oldNotifier;
    private final List<BluetoothScanningListener> mScanListeners;
    private final ILogger myLogger;
    private final Context context;
    private ISystemListener currentActivity;

    public MyRunUartConnector(EquipmentConnectionState initialState, Context context) {
        super(initialState);
        this.context = context;
        mScanListeners = new ArrayList<>();
        myLogger = Logger.getInstance();

        for (EquipmentConnectionListener l : this.mConnectionListeners) {
            myLogger.logDebug(TAG, "ConnectionListener:  "+l.getClass().getSimpleName());
        }

        oldNotifier = BluetoothConnectionNotifier.create(this.mScanListeners, this.mConnectionListeners);
        manager = FitnessMachineManager.getInstance(context);
        manager.addOnScanCallback(new FitnessMachineManager.OnScanCallback() {

            @Override
            public void onDeviceFound(FitnessBtDevice fitnessDevice) {
                BluetoothDevice device = fitnessDevice.getDevice();
                if (!devices.contains(device) && device.getName() != null && device.getName().toLowerCase().contains("myrun")) {
                    Log.w(TAG, "Device found: " + fitnessDevice.getType().toString() + " - Mac: " + device.getAddress());
                    devices.add(device);
                    oldNotifier.onBluetoothDeviceFound(device);
                }
            }

            @Override
            public void onConnected(FitnessBtDevice device) {
                Log.w(TAG, "Connected to: " + device.getDevice().getAddress());
                oldNotifier.onConnectionEstablished();
                setConnectionState(EquipmentConnectionState.CONNECTED);
            }

            @Override
            public void onConfigured(FitnessBtDevice device) {
                Log.w(TAG, "Configured device: " + device.getDevice().getAddress());
            }

            @Override
            public void onDisconnected(FitnessBtDevice device) {
                Log.w(TAG, "Disconnected");
                oldNotifier.onConnectionTerminated();
                setConnectionState(EquipmentConnectionState.IDLE);
            }
        });

        manager.addOnChangeCharacteristicCallback(new FitnessMachineManager.OnChangeCharacteristicCallback<UartRxCharacteristic>() {
            @Override
            public void onChange(UartRxCharacteristic characteristic) {
                CommandModel cmd = characteristic.getCommandModel();
                String value = getRawData(cmd);
                logCmdRx(value);
                oldNotifier.onMessageReceived(value);
            }
        }, BLEUartConstants.Characteristics.UART_RX);
    }

    @Override
    public void setup(boolean tryAutoconnect) {
        this.scanDevices();
    }

    @Override
    public void terminatePendingActions() {

    }

    @Override
    public void scanDevices() {
        devices.clear();
        manager.startScan();
        setConnectionState(EquipmentConnectionState.LISTEN);
        oldNotifier.onBluetoothScanStarted(false);
    }

    @Override
    public void connect(BluetoothDevice device) {
        manager.stopScan();
        manager.connect(device);
    }

    @Override
    public boolean readCharacteristic(UUID characteristicUuid) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void disconnect() {
        manager.disconnect();
    }

    @Override
    public void restart() {
        this.scanDevices();
    }

    @Override
    public void writeUartCommand(String tag, String data) throws WriteNotAllowedException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void sendMessage(String message) throws WriteNotAllowedException {
        String rawCmdString = message.toUpperCase().replace("@", "");
        rawCmdString = rawCmdString.replace("#", "");
        Log.w(TAG, "Original message: "+message+" - rawCmd: "+rawCmdString);
        CommandModel cmd = new CommandModel();
        cmd.setType(CommandType._TX);
        String[] rawCmdSplit = rawCmdString.split(String.valueOf((char) CommandModel.Uart.TX_PARAM_SEPARATOR));
        if (rawCmdSplit.length > 1) {
            cmd.setCmd(rawCmdSplit[0]);
            List<String> params = new ArrayList<>(Arrays.asList(rawCmdSplit));
            params.remove(0);
            cmd.setParams(params);
            try {
                if (manager.writeUartCmd(cmd)) {
                    logCmdTx(cmd.encode());
                } else {
                    Toast.makeText(context, "Device not connectedAndReady", Toast.LENGTH_SHORT).show();
                }
            } catch (ExceptionCommandUart exceptionCommandUart) {
                exceptionCommandUart.printStackTrace();
                Toast.makeText(context, exceptionCommandUart.getMessage(), Toast.LENGTH_LONG).show();
            }
        } else if (rawCmdSplit.length > 0) {
            cmd.setCmd(rawCmdSplit[0]);
            try {
                if (manager.writeUartCmd(cmd)) {
                    logCmdTx(cmd.encode());
                } else {
                    Toast.makeText(context, "Device not connectedAndReady", Toast.LENGTH_SHORT).show();
                }
            } catch (ExceptionCommandUart exceptionCommandUart) {
                exceptionCommandUart.printStackTrace();
                Toast.makeText(context, exceptionCommandUart.getMessage(), Toast.LENGTH_LONG).show();
            }

        }
    }

/*    @Override
    public void registerEquipmentConnectionListener(final EquipmentConnectionListener listener) {
        List<String> classNames = new ArrayList<>();
        for (EquipmentConnectionListener l : this.mConnectionListeners) {
            classNames.add(l.getClass().getSimpleName());
        }
        boolean sameInstanceCondition = this.mConnectionListeners.contains(listener);
        boolean sameClassCondition = classNames.contains(listener.getClass().getSimpleName());
        myLogger.logError(TAG, "Trying to register listener " + listener.toString() + ". sameInstanceCondition=" + sameInstanceCondition + ",  sameClassCondition=" + sameClassCondition);
        Log.e(TAG, "Trying to register listener " + listener.toString() + ". sameInstanceCondition=" + sameInstanceCondition + ",  sameClassCondition=" + sameClassCondition);

        if (sameClassCondition || sameInstanceCondition) {
            myLogger.logError(TAG, "Listener registration skipped");
            Log.e(TAG, "Listener registration skipped");
            return;
        }
        myLogger.logError(TAG, "Listener registration completed");
        Log.e(TAG, "Listener registration completed");
        this.mConnectionListeners.add(listener);
    }*/


    // AAA

    public void setCurrentActivity(ISystemListener activity) {
        this.currentActivity = activity;
    }

    public void stopScanningDevices() {
        //disconnect();
        manager.stopScan();
    }

    public final void registerBluetoothScanListener(final BluetoothScanningListener listener) {
        if (!this.mScanListeners.contains(listener)) {
            this.mScanListeners.add(listener);
        }
    }

    public void unregisterBluetoothScanListener(final BluetoothScanningListener listener) {
        if (mScanListeners.contains(listener)) {
            this.mScanListeners.remove(listener);
        }
    }

    public void onBluetoothAdapterTurnedOn(boolean b) {
        throw new UnsupportedOperationException();
    }


    // { Private logging

    private void logCmdRx(String value) {
        Log.w(TAG, "UART - RX: "+value);
        myLogger.logDebug(TAG, "[UART] RX: "+value);
    }

    private void logCmdTx(String value) {
        Log.w(TAG, "UART - TX: "+value);
        myLogger.logDebug(TAG, "[UART] TX: "+value);
    }

    private String getRawData(CommandModel cmd) {
        StringBuilder raw = new StringBuilder();
        raw.append(cmd.getCmd());
        for (String param : cmd.getParams()) {
            if (CommandType._TX.equals(cmd.getType())) {
                raw.append((char) CommandModel.Uart.TX_PARAM_SEPARATOR).append(param);
            } else if (CommandType._RX.equals(cmd.getType())) {
                raw.append((char) CommandModel.Uart.RX_PARAM_SEPARATOR).append(param);
            }
        }
        return raw.toString();
    }

    // }
}
