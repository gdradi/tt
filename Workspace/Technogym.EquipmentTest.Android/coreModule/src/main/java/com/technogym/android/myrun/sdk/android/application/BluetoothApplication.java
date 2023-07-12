package com.technogym.android.myrun.sdk.android.application;

import com.technogym.android.myrun.sdk.bluetooth.controllers.BluetoothController;
import com.technogym.android.myrun.sdk.bluetooth.controllers.IBluetoothController;

public class BluetoothApplication extends EquipmentApplication<IBluetoothController> {

	@Override
	protected IBluetoothController initializeConnectionController() {
		return BluetoothController.createEmpty();
	}

}
