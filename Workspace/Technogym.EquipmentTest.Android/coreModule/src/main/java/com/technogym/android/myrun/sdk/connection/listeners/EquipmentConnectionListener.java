package com.technogym.android.myrun.sdk.connection.listeners;

public interface EquipmentConnectionListener {
	
	public void onMessageReceived(final String message);

	public void onConnectionEstablished();

	public void onConnectionTerminated();

	public void onConnectionInterrupted();

	public void onConnectionFailed();

}
