package com.technogym.equipmenttest.bleuart.technogymbtleuart.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;


import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.Exceptions.ExceptionCommandUart;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandModel;
import com.technogym.equipmenttest.bleuart.technogymbtleuart.model.CommandType;

import static android.content.ContentValues.TAG;


/**
 * Created by sventurini on 20/07/16.
 */
public class UartRxCharacteristic extends BaseCharacteristic {

    public UartRxCharacteristic(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    private boolean isReading = false;
    private String mCmdRxReceived = "";
    private CommandModel commandModel = null;
    public CommandModel getCommandModel() {
        return commandModel;
    }

    public boolean getValue() {
        byte value = characteristic.getValue()[0];

        switch (value) {
            case CommandModel.Uart.START_CMD_RX:
                mCmdRxReceived = "";
                mCmdRxReceived += String.valueOf((char) value);
                isReading = true;
                break;

            case CommandModel.Uart.END_CMD_RX_2:
                mCmdRxReceived += String.valueOf((char) value);
                isReading = false;
                commandModel = new CommandModel();
                try {
                    commandModel.decode(mCmdRxReceived, CommandType._RX);

                } catch (ExceptionCommandUart exceptionCommandUart) {
                    Log.d(TAG, "Error " + exceptionCommandUart.getMessage());
                    return true;

                }
                return true;

            default:
                if (isReading) mCmdRxReceived += String.valueOf((char) value);
                break;
        }
        return false;
    }

    @Override
    public boolean elabData() {
        return getValue();
    }
}
