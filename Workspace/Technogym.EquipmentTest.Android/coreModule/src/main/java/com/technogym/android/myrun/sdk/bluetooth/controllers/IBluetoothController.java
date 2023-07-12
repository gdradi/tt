package com.technogym.android.myrun.sdk.bluetooth.controllers;

import java.util.List;

import com.technogym.android.myrun.sdk.android.activities.BluetoothEquipmentActivity;
import com.technogym.android.myrun.sdk.bluetooth.listeners.BluetoothScanningListener;
import com.technogym.android.myrun.sdk.connection.controllers.IEquipmentController;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

public interface IBluetoothController extends IEquipmentController {

	// { Bluetooth adapter

	public void onBluetoothAdapterTurnedOn(final boolean turnedOn);

	// }

	// { Bluetooth scan

	public boolean removeBond(final BluetoothDevice device);
	
	public boolean bondToDevice(final BluetoothDevice device);

	public List<BluetoothDevice> getBoundedDevices();

	public void startScanningDevices();

	public void stopScanningDevices();

	// }

	// { Bluetooth connection management

	public BluetoothDevice getConnectedDevice();

	public void connectToDevice(final BluetoothDevice device);

	public void connectedToDevice(final BluetoothSocket socket, final BluetoothDevice device);

	public void disconnectFromDevice(final BluetoothDevice device);

	// }

	// { Interaction layer between Bluetooth connection and UI

	public void registerBluetoothScanListener(final BluetoothScanningListener listener);
	
	public void unregisterBluetoothScanListener(final BluetoothScanningListener listener);

	public void setCurrentActivity(final BluetoothEquipmentActivity activity);

	// }

	// { Methods to manage Bluetooth devices

	public void removePairedDevices();
		
	// }

}
