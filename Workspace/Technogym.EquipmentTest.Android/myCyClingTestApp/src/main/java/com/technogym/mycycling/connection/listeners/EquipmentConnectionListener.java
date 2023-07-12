package com.technogym.mycycling.connection.listeners;

import com.technogym.mycycling.commands.models.ICommandReply;
import com.technogym.mycycling.connection.models.EquipmentConnectionState;
import com.technogym.mycycling.listeners.IListener;

public interface EquipmentConnectionListener extends IListener {
	
	public void onValueReceived(final ICommandReply reply);

	public void onTargetDeviceFound(String data);

	public void onConnectionStateChanged(final EquipmentConnectionState state);

	public void onUpgradeDone(final Boolean result);
}
