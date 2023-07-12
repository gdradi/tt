package com.technogym.myrow.connection.models;

public enum EquipmentConnectionState {
	STATE_DISCONNECTED(0), STATE_CONNECTING(1), STATE_CONNECTED(2);

	private final int mValue;

	private EquipmentConnectionState(final int value) {
		this.mValue = value;
	}

	public int getValue() {
		return this.mValue;
	}

}
