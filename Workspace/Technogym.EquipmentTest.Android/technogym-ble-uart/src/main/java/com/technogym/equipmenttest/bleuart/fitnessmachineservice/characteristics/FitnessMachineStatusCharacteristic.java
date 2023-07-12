package com.technogym.equipmenttest.bleuart.fitnessmachineservice.characteristics;

import android.bluetooth.BluetoothGattCharacteristic;
import android.util.Log;

import com.technogym.equipmenttest.bleuart.btlesupport.BaseCharacteristic;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.ConstantsFitnessMachine;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.OpCodeStatus;
import com.technogym.equipmenttest.bleuart.fitnessmachineservice.model.OpCodeUtilConverter;
import com.technogym.sdk.fitnessmachineservice.model.FTMStatusOp;

/**
 * Created by sventurini on 16/02/2017.
 */

public class FitnessMachineStatusCharacteristic extends BaseCharacteristic {

    public FitnessMachineStatusCharacteristic(BluetoothGattCharacteristic characteristic) {
        super(characteristic.getUuid(), characteristic.getProperties(), characteristic.getPermissions(), characteristic);
    }

    private FTMStatusOp ftmStatusOp;

    public FTMStatusOp getFtmStatusOp() {
        return ftmStatusOp;
    }

    public void setFtmStatusOp(FTMStatusOp ftmStatusOp) {
        this.ftmStatusOp = ftmStatusOp;
    }

    public FitnessMachineStatusCharacteristic() {
        super();
    }

    @Override
    public boolean elabData() {
        byte[] value = getCharacteristic().getValue();
        if (value.length <= 0) return false;
        ftmStatusOp = new FTMStatusOp();
        OpCodeStatus opCodeStatus = OpCodeUtilConverter.getOpCodeStatusFromByte(value[0]);
        ftmStatusOp.setOpCode(opCodeStatus);
        ftmStatusOp.setParameter(-1d);

        if (OpCodeStatus._OP_RESET.equals(opCodeStatus)) {
            //No parameters
        } else if (OpCodeStatus._OP_FITNESS_MACHINE_STARTED_OR_RESUMED.equals(opCodeStatus)) {
            int paramInt = value[1];
            ftmStatusOp.setParameter((double)paramInt);
        } else if (OpCodeStatus._OP_FITNESS_MACHINE_STOPPED_OR_PAUSED.equals(opCodeStatus)) {
            int paramInt = value[1];
            ftmStatusOp.setParameter((double)paramInt);
        }

        Log.d(ConstantsFitnessMachine.TAG,
                "Status change: OP code"
                + opCodeStatus.toString()
                + " ("+ value[0] + ") "
                + " Parameters : " + ftmStatusOp.getParameter()
        );
        return true;
    }
}
