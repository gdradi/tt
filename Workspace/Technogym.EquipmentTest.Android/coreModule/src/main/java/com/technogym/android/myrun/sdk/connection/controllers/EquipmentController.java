package com.technogym.android.myrun.sdk.connection.controllers;

import java.util.ArrayList;
import java.util.List;

import com.technogym.android.myrun.sdk.bluetooth.exceptions.WriteNotAllowedException;
import com.technogym.android.myrun.sdk.connection.listeners.EquipmentConnectionListener;
import com.technogym.android.myrun.sdk.connection.models.EquipmentConnectionState;

public abstract class EquipmentController implements IEquipmentController {

	protected final List<EquipmentConnectionListener> mConnectionListeners;

	protected EquipmentConnectionState mState;

	// { Construction

	protected EquipmentController(final EquipmentConnectionState initialState) {
		super();

		this.mConnectionListeners = new ArrayList<EquipmentConnectionListener>();
		this.mState = initialState;
	}

	// }

	// { IEquipmentController implementation

	@Override
	public abstract void setup(final boolean tryAutoconnect);

	@Override
	public abstract void terminatePendingActions();

	@Override
	public abstract void disconnect();

	@Override
	public abstract void restart();

	@Override
	public abstract void sendMessage(final String message) throws WriteNotAllowedException;

	@Override
	public void registerEquipmentConnectionListener(final EquipmentConnectionListener listener) {
		if (!this.mConnectionListeners.contains(listener)) {
			this.mConnectionListeners.add(listener);
		}
	}

	@Override
	public void unregisterEquipmentConnectionListener(final EquipmentConnectionListener listener) {
		if (this.mConnectionListeners.contains(listener)) {
			this.mConnectionListeners.remove(listener);
		}
	}

	@Override
	public EquipmentConnectionState getConnectionState() {
		return this.mState;
	}

	@Override
	public void setConnectionState(final EquipmentConnectionState state) {
		this.mState = state;
	}

	@Override
	public List<EquipmentConnectionListener> getRegisteredListeners() {
		return this.mConnectionListeners;
	}

	// }

}
