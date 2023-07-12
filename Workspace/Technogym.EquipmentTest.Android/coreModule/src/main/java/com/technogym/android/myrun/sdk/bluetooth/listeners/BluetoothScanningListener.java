package com.technogym.android.myrun.sdk.bluetooth.listeners;

import android.bluetooth.BluetoothDevice;

public interface BluetoothScanningListener {

	public void onBluetoothScanStarted(final boolean isReboot);
	
	public void onBluetoothScanTerminated();

	public void onBluetoothDeviceFound(final BluetoothDevice device);

    public void onLowKitUpgraded(boolean result);
	public void onHighKitUpgraded(boolean result);
}
