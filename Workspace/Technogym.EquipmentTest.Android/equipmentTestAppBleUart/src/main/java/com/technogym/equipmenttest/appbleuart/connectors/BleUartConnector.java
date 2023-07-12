package com.technogym.equipmenttest.appbleuart.connectors;

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
import com.technogym.sdk.fitnessmachineservice.interfaces.OTAListener;
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

public class BleUartConnector extends EquipmentController implements IEquipmentController, IBleController {

    private static final String TAG = BleUartConnector.class.getSimpleName();
    private final FitnessMachineManager manager; // Questo è di Technogym
    private final List<BluetoothDevice> devices = new ArrayList<>();
    private final IBluetoothConnectionNotifier oldNotifier;
    private final List<BluetoothScanningListener> mScanListeners;
    private final ILogger myLogger;
    private final Context context;
    private ISystemListener currentActivity;
    private int rebootRetryCounter;

    public BleUartConnector(EquipmentConnectionState initialState, Context context) {
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
                if (!devices.contains(device) && device.getName() != null &&
                        (device.getName().toLowerCase().contains("my") || device.getName().toLowerCase().contains("essentialled"))) {
                    myLogger.logDebug(TAG, "Device found: " + fitnessDevice.getType().toString() + " - Mac: " + device.getAddress());
                    devices.add(device);
                    oldNotifier.onBluetoothDeviceFound(device);
                }
            }

            @Override
            public void onConnected(FitnessBtDevice device) {
                myLogger.logDebug(TAG, "[onConnected] Connected to: " + device.toString());

                oldNotifier.onConnectionEstablished();
                setConnectionState(EquipmentConnectionState.CONNECTED);

            }

            @Override
            public void onConfigured(FitnessBtDevice device) {
                myLogger.logDebug(TAG, "onConfigured device: " + device.getDevice().getAddress());
            }

            @Override
            public void onDisconnected(FitnessBtDevice device) {
                myLogger.logDebug(TAG, "onDisconnected");
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

    public FitnessBtDevice getConnectedDevice(){
        return manager.getConnectedDevice();
    }

    @Override
    public void scanDevices() {
        myLogger.logDebug(TAG,"[scanDevices] Calling onBluetoothScanStarted(false)");
        devices.clear();
        manager.startScan();
        setConnectionState(EquipmentConnectionState.LISTEN);
        boolean showDevicesModal = false;
        oldNotifier.onBluetoothScanStarted(false);
    }

    public void reconnectAfterReboot(int pollingTime) throws InterruptedException {
        myLogger.logDebug(TAG,"[reconnectAfterReboot] pollingTime: "+pollingTime);

        rebootRetryCounter = 0;
        devices.clear();
        manager.startScan();
        setConnectionState(EquipmentConnectionState.LISTEN);

        if(pollingTime == 0){
            // Caso riconnessione a seguito di STG_ID
            oldNotifier.onBluetoothScanStarted(true);
        }else{
            // Caso riconnessione a seguito di upgrade LK e BT

            while (manager.getConnectedDevice() == null ){
                myLogger.logDebug(TAG,"[reconnectAfterReboot] manager.getConnectedDevice(): "+manager.getConnectedDevice());
                myLogger.logDebug(TAG,"[reconnectAfterReboot] Trying to connect (number "+rebootRetryCounter+")");

                rebootRetryCounter++;
                oldNotifier.onBluetoothScanStarted(true);

                // Da quando si chiama la connect() a quando il device è effettivamente connesso passa qualche secondo.
                // La sleep viene fatta per evitare che vengano effettuate più connessioni sovrapposte.
                // Se ci sono più connessioni il canale UART si "satura" e fornisce risposte multiple
                // Tempo minimo per non scaturire l'anomalia: 6 sec
                Thread.sleep(pollingTime);
            }
        }
    }

    public void onLowKitUpgraded(boolean result){
        myLogger.logDebug(TAG,"[onLowKitUpgraded] result: "+result);
        oldNotifier.onLowKitUpgraded(result);
    }

    public void onHighKitUpgraded(boolean result){
        myLogger.logDebug(TAG,"[onHighKitUpgraded] result: "+result);
        oldNotifier.onHighKitUpgraded(result);
    }

    public void writeFirmwareData(String path, OTAListener listener){
        myLogger.logDebug(TAG,"[writeFirmwareData] path: "+path);
        myLogger.logDebug(TAG,"[writeFirmwareData] listener: "+listener);
        manager.writeFirmwareData(path,listener);
    }


    @Override
    public void connect(BluetoothDevice device) {
        myLogger.logDebug(TAG, " Trying to connect to device: " + device.getAddress());
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
        Log.w(TAG, " Original message: "+message+" - rawCmd: "+rawCmdString);
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
