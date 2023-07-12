package com.technogym.android.myrun.sdk.bluetooth;

public class BluetoothDeviceConnectionFactory implements IBluetoothDeviceConnectionFactory {

	// { Constructions

	protected BluetoothDeviceConnectionFactory() {
		super();
	}

	public static IBluetoothDeviceConnectionFactory create() {
		return new BluetoothDeviceConnectionFactory();
	}

	// }

}
