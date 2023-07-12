package com.technogym.equipmenttest.bleuart.fitnessmachineservice.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * OpCodeStatus
 *
 */
public class OpCodeStatus implements Parcelable {
	public static OpCodeStatus _OP_RESET = new OpCodeStatus("OP_RESET");
	public static OpCodeStatus _OP_FITNESS_MACHINE_STOPPED_OR_PAUSED = new OpCodeStatus("OP_FITNESS_MACHINE_STOPPED_OR_PAUSED");
	public static OpCodeStatus _OP_FITNESS_MACHINE_STARTED_OR_RESUMED = new OpCodeStatus("OP_FITNESS_MACHINE_STARTED_OR_RESUMED");
	public static OpCodeStatus _OP_TARGET_SPEED_CHANGED = new OpCodeStatus("OP_TARGET_SPEED_CHANGED");
	public static OpCodeStatus _OP_TARGET_INCLINE_CHANGED = new OpCodeStatus("OP_TARGET_INCLINE_CHANGED");
	public static OpCodeStatus _OP_TARGET_EXPENDED_ENERGY_CHANGED = new OpCodeStatus("OP_TARGET_EXPENDED_ENERGY_CHANGED");
	public static OpCodeStatus _OP_TARGET_DISTANCE_CHANGED = new OpCodeStatus("OP_TARGET_DISTANCE_CHANGED");
	public static OpCodeStatus _OP_TARGET_TRAINING_TIME_CHANGED = new OpCodeStatus("OP_TARGET_TRAINING_TIME_CHANGED");
	public static OpCodeStatus _OP_CONTROL_PERMISSION_LOST = new OpCodeStatus("OP_CONTROL_PERMISSION_LOST");
	public static OpCodeStatus _OP_TARGET_REST_TIME_CHANGED = new OpCodeStatus("OP_TARGET_REST_TIME_CHANGED");
	public static OpCodeStatus _OP_TARGET_DISTANCE_INTERVAL_TRAINING_CHANGED = new OpCodeStatus("OP_TARGET_DISTANCE_INTERVAL_TRAINING_CHANGED");
	public static OpCodeStatus _OP_TARGET_TIME_INTERVAL_TRAINING_CHANGED = new OpCodeStatus("OP_TARGET_TIME_INTERVAL_TRAINING_CHANGED");
	public static OpCodeStatus _OP_TARGET_TEST_DISTANCE_CHANGED = new OpCodeStatus("OP_TARGET_TEST_DISTANCE_CHANGED");
	public static OpCodeStatus _OP_TARGET_TEST_TIME_CHANGED = new OpCodeStatus("OP_TARGET_TEST_TIME_CHANGED");
	public static OpCodeStatus _UNDEFINED_ = new OpCodeStatus("_UNDEFINED_");

	private final String mValue;

	private OpCodeStatus(String value) {
		mValue = value;
	}

	@Override
	public int hashCode() {
		return mValue.hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof OpCodeStatus) {
			return mValue.equals(((OpCodeStatus) o).mValue);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return mValue;
	}

	public static OpCodeStatus valueOf(String value) {
		if (value == null) {
			return null;
		}

		switch (value) {
			case "OP_RESET":
				return _OP_RESET;
			case "OP_FITNESS_MACHINE_STOPPED_OR_PAUSED":
				return _OP_FITNESS_MACHINE_STOPPED_OR_PAUSED;
			case "OP_FITNESS_MACHINE_STARTED_OR_RESUMED":
				return _OP_FITNESS_MACHINE_STARTED_OR_RESUMED;
			case "OP_TARGET_SPEED_CHANGED":
				return _OP_TARGET_SPEED_CHANGED;
			case "OP_TARGET_INCLINE_CHANGED":
				return _OP_TARGET_INCLINE_CHANGED;
			case "OP_TARGET_EXPENDED_ENERGY_CHANGED":
				return _OP_TARGET_EXPENDED_ENERGY_CHANGED;
			case "OP_TARGET_DISTANCE_CHANGED":
				return _OP_TARGET_DISTANCE_CHANGED;
			case "OP_TARGET_TRAINING_TIME_CHANGED":
				return _OP_TARGET_TRAINING_TIME_CHANGED;
			case "OP_CONTROL_PERMISSION_LOST":
				return _OP_CONTROL_PERMISSION_LOST;
			case "OP_TARGET_REST_TIME_CHANGED":
				return _OP_TARGET_REST_TIME_CHANGED;
			case "OP_TARGET_DISTANCE_INTERVAL_TRAINING_CHANGED":
				return _OP_TARGET_DISTANCE_INTERVAL_TRAINING_CHANGED;
			case "OP_TARGET_TIME_INTERVAL_TRAINING_CHANGED":
				return _OP_TARGET_TIME_INTERVAL_TRAINING_CHANGED;
			case "OP_TARGET_TEST_DISTANCE_CHANGED":
				return _OP_TARGET_TEST_DISTANCE_CHANGED;
			case "OP_TARGET_TEST_TIME_CHANGED":
				return _OP_TARGET_TEST_TIME_CHANGED;
			default:
				return _UNDEFINED_;
		}
	}

	/************************ Parcel Serialization ************************/
	public static final Creator<OpCodeStatus> CREATOR = new Creator<OpCodeStatus>() {
		public OpCodeStatus createFromParcel(Parcel in) {
			return new OpCodeStatus(in);
		}

		public OpCodeStatus[] newArray(int size) {
			return new OpCodeStatus[size];
		}
	};

	private OpCodeStatus(Parcel in) {
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
