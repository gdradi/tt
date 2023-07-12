package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jetbrains.annotations.NotNull;

/**
 * OpCodeControl
 */
public class OpCodeControl implements Parcelable {
    public static OpCodeControl _OP_REQUEST_CONTROL = new OpCodeControl("OP_REQUEST_CONTROL");
    public static OpCodeControl _OP_RESET = new OpCodeControl("OP_RESET");
    public static OpCodeControl _OP_SET_TARGET_SPEED = new OpCodeControl(
            "OP_SET_TARGET_SPEED");
    public static OpCodeControl _OP_SET_TARGET_INCLINATION = new OpCodeControl(
            "OP_SET_TARGET_INCLINATION");
    public static OpCodeControl _OP_SET_TARGET_RESISTANCE_LEVEL = new OpCodeControl(
            "OP_SET_TARGET_RESISTANCE_LEVEL");
    public static OpCodeControl _OP_SET_TARGET_POWER = new OpCodeControl(
            "OP_SET_TARGET_POWER");
    public static OpCodeControl _OP_SET_TARGET_HEART_RATE = new OpCodeControl(
            "OP_SET_TARGET_HEART_RATE");
    public static OpCodeControl _OP_START_RESUME = new OpCodeControl("OP_START_RESUME");
    public static OpCodeControl _OP_STOP_PAUSE = new OpCodeControl("OP_STOP_PAUSE");
    public static OpCodeControl _OP_SET_TARGETED_EXPENDED_ENERGY = new OpCodeControl(
            "OP_SET_TARGETED_EXPENDED_ENERGY");
    public static OpCodeControl _OP_SET_TARGETED_NUMBER_OF_STEPS = new OpCodeControl(
            "OP_SET_TARGETED_NUMBER_OF_STEPS");
    public static OpCodeControl _OP_SET_TARGETED_NUMBER_OF_STRIDES = new OpCodeControl(
            "OP_SET_TARGETED_NUMBER_OF_STRIDES");
    public static OpCodeControl _OP_SET_TARGETED_DISTANCE = new OpCodeControl(
            "OP_SET_TARGETED_DISTANCE");
    public static OpCodeControl _OP_SET_TARGETED_TRAINING_TIME = new OpCodeControl(
            "OP_SET_TARGETED_TRAINING_TIME");
    public static OpCodeControl _OP_SET_TARGETED_TIME_IN_TWO_HR_ZONES = new OpCodeControl(
            "OP_SET_TARGETED_TIME_IN_TWO_HR_ZONES");
    public static OpCodeControl _OP_SET_TARGETED_TIME_IN_THREE_HR_ZONES = new OpCodeControl(
            "OP_SET_TARGETED_TIME_IN_THREE_HR_ZONES");
    public static OpCodeControl _OP_SET_TARGETED_TIME_IN_FIVE_HR_ZONES = new OpCodeControl(
            "OP_SET_TARGETED_TIME_IN_FIVE_HR_ZONES");
    public static OpCodeControl _OP_SET_INDOOR_BIKE_SIMULATION_PARAM = new OpCodeControl(
            "OP_SET_INDOOR_BIKE_SIMULATION_PARAM");
    public static OpCodeControl _OP_SET_WHEEL_CIRCUMFERENCE = new OpCodeControl(
            "OP_SET_WHEEL_CIRCUMFERENCE");
    public static OpCodeControl _OP_SET_SPIN_DOWN_CONTROL = new OpCodeControl(
            "OP_SET_SPIN_DOWN_CONTROL");
    public static OpCodeControl _OP_SET_TARGETED_CADENCE = new OpCodeControl(
            "OP_SET_TARGETED_CADENCE");
    public static OpCodeControl _OP_RESPONSE_CODE = new OpCodeControl(
            "OP_RESPONSE_CODE");
    public static OpCodeControl _OP_SET_COOLDOWN = new OpCodeControl(
            "OP_SET_COOLDOWN");
    public static OpCodeControl _OP_SET_TARGETED_REST_TIME = new OpCodeControl(
            "OP_SET_TARGETED_REST_TIME");
    public static OpCodeControl _OP_SET_TARGETED_DISTANCE_AND_REST_TIME_OF_INTERVAL_TRAINING = new OpCodeControl(
            "OP_SET_TARGETED_DISTANCE_AND_REST_TIME_OF_INTERVAL_TRAINING");
    public static OpCodeControl _OP_SET_TARGETED_DURATION_AND_REST_TIME_OF_INTERVAL_TRAINING = new OpCodeControl(
            "OP_SET_TARGETED_DURATION_AND_REST_TIME_OF_INTERVAL_TRAINING");
    public static OpCodeControl _OP_SET_TARGETED_TEST_DISTANCE = new OpCodeControl(
            "OP_SET_TARGETED_TEST_DISTANCE");
    public static OpCodeControl _OP_SET_TARGETED_TEST_TIME = new OpCodeControl(
            "OP_SET_TARGETED_TEST_TIME");
    public static OpCodeControl _UNDEFINED_ = new OpCodeControl("_UNDEFINED_");

    private final String mValue;

    private OpCodeControl(String value) {
        mValue = value;
    }

    @Override
    public int hashCode() {
        return mValue.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof OpCodeControl) {
            return mValue.equals(((OpCodeControl) o).mValue);
        } else {
            return false;
        }
    }

    @NotNull
    @Override
    public String toString() {
        return mValue;
    }

    public static OpCodeControl valueOf(String value) {
        if (value == null) {
            return null;
        }

        switch (value) {
            case "OP_REQUEST_CONTROL":
                return _OP_REQUEST_CONTROL;
            case "OP_RESET":
                return _OP_RESET;
            case "OP_SET_TARGET_SPEED":
                return _OP_SET_TARGET_SPEED;
            case "OP_SET_TARGET_INCLINATION":
                return _OP_SET_TARGET_INCLINATION;
            case "OP_SET_TARGET_RESISTANCE_LEVEL":
                return _OP_SET_TARGET_RESISTANCE_LEVEL;
            case "OP_SET_TARGET_POWER":
                return _OP_SET_TARGET_POWER;
            case "OP_SET_TARGET_HEART_RATE":
                return _OP_SET_TARGET_HEART_RATE;
            case "OP_START_RESUME":
                return _OP_START_RESUME;
            case "OP_STOP_PAUSE":
                return _OP_STOP_PAUSE;
            case "OP_SET_TARGETED_EXPENDED_ENERGY":
                return _OP_SET_TARGETED_EXPENDED_ENERGY;
            case "OP_SET_TARGETED_NUMBER_OF_STEPS":
                return _OP_SET_TARGETED_NUMBER_OF_STEPS;
            case "OP_SET_TARGETED_NUMBER_OF_STRIDES":
                return _OP_SET_TARGETED_NUMBER_OF_STRIDES;
            case "OP_SET_TARGETED_DISTANCE":
                return _OP_SET_TARGETED_DISTANCE;
            case "OP_SET_TARGETED_TRAINING_TIME":
                return _OP_SET_TARGETED_TRAINING_TIME;
            case "OP_SET_TARGETED_TIME_IN_TWO_HR_ZONES":
                return _OP_SET_TARGETED_TIME_IN_TWO_HR_ZONES;
            case "OP_SET_TARGETED_TIME_IN_THREE_HR_ZONES":
                return _OP_SET_TARGETED_TIME_IN_THREE_HR_ZONES;
            case "OP_SET_TARGETED_TIME_IN_FIVE_HR_ZONES":
                return _OP_SET_TARGETED_TIME_IN_FIVE_HR_ZONES;
            case "OP_SET_INDOOR_BIKE_SIMULATION_PARAM":
                return _OP_SET_INDOOR_BIKE_SIMULATION_PARAM;
            case "OP_SET_WHEEL_CIRCUMFERENCE":
                return _OP_SET_WHEEL_CIRCUMFERENCE;
            case "OP_SET_SPIN_DOWN_CONTROL":
                return _OP_SET_SPIN_DOWN_CONTROL;
            case "OP_SET_TARGETED_CADENCE":
                return _OP_SET_TARGETED_CADENCE;
            case "OP_RESPONSE_CODE":
                return _OP_RESPONSE_CODE;
            case "OP_SET_TARGETED_REST_TIME":
                return _OP_SET_TARGETED_REST_TIME;
            case "OP_SET_COOLDOWN":
                return _OP_SET_COOLDOWN;
            case "OP_SET_TARGETED_DISTANCE_AND_REST_TIME_OF_INTERVAL_TRAINING":
                return _OP_SET_TARGETED_DISTANCE_AND_REST_TIME_OF_INTERVAL_TRAINING;
            case "OP_SET_TARGETED_DURATION_AND_REST_TIME_OF_INTERVAL_TRAINING":
                return _OP_SET_TARGETED_DURATION_AND_REST_TIME_OF_INTERVAL_TRAINING;
            case "OP_SET_TARGETED_TEST_DISTANCE":
                return _OP_SET_TARGETED_TEST_DISTANCE;
            case "OP_SET_TARGETED_TEST_TIME":
                return _OP_SET_TARGETED_TEST_TIME;
            default:
                return _UNDEFINED_;
        }
    }

    /************************ Parcel Serialization ************************/
    public static final Creator<OpCodeControl> CREATOR = new Creator<OpCodeControl>() {
        public OpCodeControl createFromParcel(Parcel in) {
            return new OpCodeControl(in);
        }

        public OpCodeControl[] newArray(int size) {
            return new OpCodeControl[size];
        }
    };

    private OpCodeControl(Parcel in) {
        mValue = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mValue);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
