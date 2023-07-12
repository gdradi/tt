package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.technogym.equipmenttest.bleuart.fitnessmachineservice.Utils;


/**
 * Created by sventurini on 22/02/2017.
 */

public class FTMControlPointOp implements Parcelable {

    public static final String OPCODE = "opCodeControl";
    public static final String PARAMETER = "parameter";

    private OpCodeControl opCodeControl;
    private double parameter;

    public FTMControlPointOp(OpCodeControl opCodeControl, double parameter) {
        this.opCodeControl = opCodeControl;
        this.parameter = parameter;
    }

    public OpCodeControl getOpCodeControl() {
        return opCodeControl;
    }

    public void setOpCodeControl(OpCodeControl opCodeControl) {
        this.opCodeControl = opCodeControl;
    }

    public double getParameter() {
        return parameter;
    }

    public void setParameter(double parameter) {
        this.parameter = parameter;
    }

    public byte[] getRawData() {
        byte opCode = OpCodeUtilConverter.getByteFromOpCodeControl(this.opCodeControl);
        byte[] paramValue = convertParamToByte(this.opCodeControl, this.parameter);

        return Utils.joinByteAndByteArray(opCode, paramValue);
    }

    private byte[] convertParamToByte(OpCodeControl opCodeControl, double parameter) {
        if (opCodeControl.equals(OpCodeControl._OP_REQUEST_CONTROL)
                || opCodeControl.equals(OpCodeControl._OP_START_RESUME)
                || opCodeControl.equals(OpCodeControl._OP_RESET)) {
            //No parameters
            return new byte[0];
        } else if (opCodeControl.equals(OpCodeControl._OP_STOP_PAUSE)) {
            //UINT8
            byte[] rawParam = new byte[1];
            int parameterInt = (int) parameter;
            rawParam[0] = (byte) parameterInt;
            return rawParam;
        } else if (opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_DISTANCE)) {
            //UINT24, res of 1
            byte[] rawParam = new byte[3];
            int parameterInt = (int) parameter;
            rawParam[0] = (byte) (parameterInt & 0xFF);
            rawParam[1] = (byte) (parameterInt >> 8 & 0xFF);
            rawParam[2] = (byte) (parameterInt >> 16 & 0xFF);
            return rawParam;
        } else if (opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_TRAINING_TIME)
                || opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_EXPENDED_ENERGY)
                || opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_NUMBER_OF_STRIDES)
                || opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_NUMBER_OF_STEPS)
                || opCodeControl.equals(OpCodeControl._OP_SET_TARGETED_REST_TIME)) {
            //UINT16, res of 1
            byte[] rawParam = new byte[2];
            int parameterInt = (int) parameter;
            rawParam[0] = (byte) (parameterInt & 0xFF);
            rawParam[1] = (byte) (parameterInt >> 8 & 0xFF);
            return rawParam;
        } else if (opCodeControl.equals(OpCodeControl._OP_SET_TARGET_SPEED)) {
            //UINT16, res of 0.01
            byte[] rawParam = new byte[2];
            int parameterInt = (int) (parameter * 100);
            rawParam[0] = (byte) (parameterInt & 0xFF);
            rawParam[1] = (byte) (parameterInt >> 8 & 0xFF);
            return rawParam;
        } else if (opCodeControl.equals(OpCodeControl._OP_SET_TARGET_INCLINATION)) {
            //SINT16, res of 0.1
            byte[] rawParam = new byte[2];
            int parameterInt = (int) (parameter * 10);
            rawParam[0] = (byte) (parameterInt);
            rawParam[1] = (byte) (parameterInt >> 8);
            return rawParam;
        }
        return new byte[0];
    }

    /************************ Parcel Serialization ************************/
    public static final Creator<FTMControlPointOp> CREATOR = new Creator<FTMControlPointOp>() {
        public FTMControlPointOp createFromParcel(Parcel in) {
            return new FTMControlPointOp(in);
        }

        public FTMControlPointOp[] newArray(int size) {
            return new FTMControlPointOp[size];
        }
    };

    private FTMControlPointOp(Parcel in) {
        opCodeControl = (OpCodeControl) in.readValue(OpCodeControl.class.getClassLoader());
        parameter = (Double) in.readValue(Double.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(opCodeControl);
        dest.writeValue(parameter);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
