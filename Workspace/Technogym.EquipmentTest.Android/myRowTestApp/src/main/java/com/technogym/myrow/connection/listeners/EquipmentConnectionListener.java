package com.technogym.myrow.connection.listeners;

import com.technogym.myrow.commands.models.ICommandReply;
import com.technogym.myrow.connection.models.EquipmentConnectionState;
import com.technogym.myrow.listeners.IListener;

public interface EquipmentConnectionListener extends IListener {
	
	public void onValueReceived(final ICommandReply reply);

	public void onTargetDeviceFound(String data);

	public void onConnectionStateChanged(final EquipmentConnectionState state);

	public void onUpgradeDone(final Boolean result);
}
