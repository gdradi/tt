package com.technogym.android.myrun.sdk.connection.controllers;

import java.util.List;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;

public interface IEquipmentController {

	// { Initialization

	public void setup(final boolean tryAutoconnect);

	public void terminatePendingActions();

	public void disconnect();

	public void restart();

	// }

	// { Interaction layer between equipment connection controller and UI

	public void sendMessage(final String message) throws WriteNotAllowedException;

	public void registerEquipmentConnectionListener(final EquipmentConnectionListener listener);

	public void unregisterEquipmentConnectionListener(final EquipmentConnectionListener listener);

	// }

	// { Getters and setters

	public EquipmentConnectionState getConnectionState();

	public void setConnectionState(final EquipmentConnectionState state);

	public List<EquipmentConnectionListener> getRegisteredListeners();

	// }

}
