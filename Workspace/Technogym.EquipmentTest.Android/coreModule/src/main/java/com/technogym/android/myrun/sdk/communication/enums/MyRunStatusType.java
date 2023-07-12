package com.technogym.android.myrun.sdk.communication.enums;

public enum MyRunStatusType {
	WAITING_FOR_START(2), RUNNING(4), COOLDOWN(6), STOPPING_MOTORS(8), EMERGENCY(10), WORKOUT_COMPLETED(12), PAUSED(14), PAUSING(
			15);

	private final int mValue;

	private MyRunStatusType(final int value) {
		this.mValue = value;
	}

	public int getValue() {
		return this.mValue;
	}

	public static MyRunStatusType valueOf(final int value) {
		switch (value) {
			case 2:
				return MyRunStatusType.WAITING_FOR_START;
			case 4:
				return MyRunStatusType.RUNNING;
			case 6:
				return MyRunStatusType.COOLDOWN;
			case 8:
				return MyRunStatusType.STOPPING_MOTORS;
			case 10:
				return MyRunStatusType.EMERGENCY;
			case 12:
				return MyRunStatusType.WORKOUT_COMPLETED;
			case 14:
				return MyRunStatusType.PAUSED;
			default:
				return MyRunStatusType.PAUSING;
		}
	}
}
