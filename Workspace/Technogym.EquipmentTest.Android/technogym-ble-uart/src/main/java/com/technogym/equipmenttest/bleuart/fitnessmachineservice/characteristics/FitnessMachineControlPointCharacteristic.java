package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.OpCodeControl;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.OpCodeUtilConverter;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.ResultCode;


/**
 * Created by sventurini on 16/02/2017.
 */

public class FitnessMachineControlPointCharacteristic extends BaseCharacteristic {

    public FitnessMachineControlPointCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);

    }

    public FitnessMachineControlPointCharacteristic() {
        super();
    }

    private ResultCode resultCode;
    private OpCodeControl resultOpCodeControl;

    public ResultCode getResultCode() {
        return resultCode;
    }

    public OpCodeControl getResultOpCodeControl() {
        return resultOpCodeControl;
    }

    @Override
    public boolean elabData() {
        byte[] value = getCharacteristic().getValue();

        resultOpCodeControl = OpCodeControl._UNDEFINED_;
        resultCode = ResultCode.UNDEFINED;
        int resultCodeValue =  -1;

        if (value.length <= 0) resultCode = ResultCode.UNDEFINED;
        else if (value[0] != (byte) 128) resultCode = ResultCode.UNDEFINED;
        else if (value.length >= 3) {
            resultOpCodeControl = OpCodeUtilConverter.getOpCodeControlFromByte(value[1]);
            resultCodeValue = value[2];
            switch (resultCodeValue) {
                case 1:
                    resultCode = ResultCode.SUCCESS;
                    break;
                case 2:
                    resultCode = ResultCode.OP_CODE_NOT_SUPPORTED;
                    break;
                case 3:
                    resultCode = ResultCode.INVALID_PARAMETER;
                    break;
                case 4:
                    resultCode = ResultCode.OPERATION_FAILED;
                    break;
                default:
                    resultCode = ResultCode.UNDEFINED;
                    break;
            }
        }

        Log.d(ConstantsFitnessMachine.TAG, "Control point indicate: " + resultOpCodeControl.toString() + " - resultCode: " + resultCodeValue);
        return true;
    }
}
