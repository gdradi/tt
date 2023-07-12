package com.technogym.myrow.models.actions;

public enum ActionType {
	CHECK(0), YESNO(1), COLOR(2), CHECK_PRESENCE(3), AUTO(4), ACCESSORIES_DISPLAY(5), IMAGE(6);

	private final int mValue;

	private ActionType(final int value) {
		this.mValue = value;
	}

	public int getValue() {
		return this.mValue;
	}
}
