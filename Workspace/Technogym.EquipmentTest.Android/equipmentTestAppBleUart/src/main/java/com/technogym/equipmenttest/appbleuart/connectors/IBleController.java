package com.technogym.equipmenttest.appbleuart.connectors;

import android.bluetooth.BluetoothDevice;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;

import java.util.UUID;

public interface IBleController {

    void scanDevices();
    void connect(BluetoothDevice device);
    boolean readCharacteristic(UUID characteristicUuid);
    void writeUartCommand(String tag, String data) throws WriteNotAllowedException;
    void disconnect();

}
