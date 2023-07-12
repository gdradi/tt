package com.technogym.equipmenttest.myrun.connectors;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.Nullable;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;

import java.util.UUID;

public interface IBleController {

    void scanDevices();
    void connect(BluetoothDevice device);
    boolean readCharacteristic(UUID characteristicUuid);
    void writeUartCommand(String tag, String data) throws WriteNotAllowedException;
    void disconnect();

}
