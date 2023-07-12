package com.technogym.android.myrun.sdk.connection.listeners;

import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;

public interface OnEquipmentConnectionStateChangedListener {

	public void onStateChanged(final EquipmentConnectionState state);

}
