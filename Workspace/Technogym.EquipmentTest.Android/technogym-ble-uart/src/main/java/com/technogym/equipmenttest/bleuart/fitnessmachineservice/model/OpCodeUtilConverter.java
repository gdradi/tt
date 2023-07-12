package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import java.util.HashMap;

/**
 * Created by sventurini on 22/02/2017.
 */
public class OpCodeUtilConverter {

    static HashMap<OpCodeControl, Byte> sOpCodeControlByte;

    static {
        sOpCodeControlByte = new HashMap<>();
        sOpCodeControlByte.put(OpCodeControl._OP_REQUEST_CONTROL, (byte) 0);
        sOpCodeControlByte.put(OpCodeControl._OP_RESET, (byte) 1);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGET_SPEED, (byte) 2);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGET_INCLINATION, (byte) 3);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGET_RESISTANCE_LEVEL, (byte) 4);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGET_POWER, (byte) 5);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGET_HEART_RATE, (byte) 6);
        sOpCodeControlByte.put(OpCodeControl._OP_START_RESUME, (byte) 7);
        sOpCodeControlByte.put(OpCodeControl._OP_STOP_PAUSE, (byte) 8);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_EXPENDED_ENERGY, (byte) 9); //Param: UINT16 in calories with resolution 1 calorie.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_NUMBER_OF_STEPS, (byte) 10); //Param: UINT16 in steps with resolution 1 step.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_NUMBER_OF_STRIDES, (byte) 11);//Param: UINT16 in stride with resolution 1 stride.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_DISTANCE, (byte) 12); //Param: UINT24 in meters with resolution 1 meter.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TRAINING_TIME, (byte) 13);//Param: UINT16 in seconds with resolution 1 second.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TIME_IN_TWO_HR_ZONES, (byte) 14); //Param: Targeted time array
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TIME_IN_THREE_HR_ZONES, (byte) 15); //Param: Targeted time array
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TIME_IN_FIVE_HR_ZONES, (byte) 16); //Param: Targeted time array
        sOpCodeControlByte.put(OpCodeControl._OP_SET_INDOOR_BIKE_SIMULATION_PARAM, (byte) 17);  //Param: ???
        sOpCodeControlByte.put(OpCodeControl._OP_SET_WHEEL_CIRCUMFERENCE, (byte) 18);  //Param: ???
        sOpCodeControlByte.put(OpCodeControl._OP_SET_SPIN_DOWN_CONTROL, (byte) 19);  //Param: ???
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_CADENCE, (byte) 20);  //Param: ???
        sOpCodeControlByte.put(OpCodeControl._OP_RESPONSE_CODE, (byte) 128);  //Param: ???

        //CUSTOM OP
        sOpCodeControlByte.put(OpCodeControl._OP_SET_COOLDOWN, (byte) 48);
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_REST_TIME, (byte) 96);//Param: UINT16 in seconds with resolution 1 second. (max 60)
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_DISTANCE_AND_REST_TIME_OF_INTERVAL_TRAINING, (byte) 97);//Param: UINT24 in meters with resolution 1 meter. UINT8 in seconds resolution 1 second (max 60)
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_DURATION_AND_REST_TIME_OF_INTERVAL_TRAINING, (byte) 98);//Param: UINT24 in second with resolution 1 second. UINT8 in seconds resolution 1 second (max 60)
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TEST_DISTANCE, (byte) 99);//Param: UINT24 in meters with resolution 1 meter.
        sOpCodeControlByte.put(OpCodeControl._OP_SET_TARGETED_TEST_TIME, (byte) 100);//Param: UINT24 in meters with resolution 1 meter.
    }

    public static byte getByteFromOpCodeControl(OpCodeControl opCodeControl) {
        return sOpCodeControlByte.get(opCodeControl);
    }

    public static OpCodeControl getOpCodeControlFromByte(byte value) {
        for (OpCodeControl opCodeControl : sOpCodeControlByte.keySet()) {
            if (sOpCodeControlByte.get(opCodeControl) == value) return opCodeControl;
        }
        return OpCodeControl._UNDEFINED_;
    }

    //--------------Fitness machine Status Utility---------------------

    static HashMap<OpCodeStatus, Byte> sOpCodeStatusByte;

    static {
        sOpCodeStatusByte = new HashMap<>();
        sOpCodeStatusByte.put(OpCodeStatus._OP_RESET, (byte) 1);
        sOpCodeStatusByte.put(OpCodeStatus._OP_FITNESS_MACHINE_STOPPED_OR_PAUSED, (byte) 2);
        sOpCodeStatusByte.put(OpCodeStatus._OP_FITNESS_MACHINE_STARTED_OR_RESUMED, (byte) 4);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_SPEED_CHANGED, (byte) 5);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_INCLINE_CHANGED, (byte) 6);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_EXPENDED_ENERGY_CHANGED, (byte) 10);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_DISTANCE_CHANGED, (byte) 13);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_TRAINING_TIME_CHANGED, (byte) 14);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_REST_TIME_CHANGED, (byte) 224);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_DISTANCE_INTERVAL_TRAINING_CHANGED, (byte) 225);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_TIME_INTERVAL_TRAINING_CHANGED, (byte) 226);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_TEST_DISTANCE_CHANGED, (byte) 227);
        sOpCodeStatusByte.put(OpCodeStatus._OP_TARGET_TEST_TIME_CHANGED, (byte) 228);
        sOpCodeStatusByte.put(OpCodeStatus._OP_CONTROL_PERMISSION_LOST, (byte) 255);
    }

    public static byte getByteFromOpCodeStatus(OpCodeStatus opCodeStatus) {
        return sOpCodeStatusByte.get(opCodeStatus);
    }

    public static OpCodeStatus getOpCodeStatusFromByte(byte value) {
        for (OpCodeStatus opCodeStatus : sOpCodeStatusByte.keySet()) {
            if (sOpCodeStatusByte.get(opCodeStatus) == value) return opCodeStatus;
        }
        return OpCodeStatus._UNDEFINED_;
    }

}
