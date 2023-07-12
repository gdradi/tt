package com.technogym.android.myrun.sdk.connection.models;

public enum EquipmentConnectionState {
	IDLE(0), LISTEN(1), CONNECTING(2), CONNECTED(3), DISCONNECTING(4);

	private final int mValue;

	private EquipmentConnectionState(final int value) {
		this.mValue = value;
	}

	public int getValue() {
		return this.mValue;
	}

}
