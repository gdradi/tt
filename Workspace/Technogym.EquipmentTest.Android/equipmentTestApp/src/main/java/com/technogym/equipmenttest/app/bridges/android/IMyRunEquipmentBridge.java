package com.technogym.equipmenttest.app.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.equipmenttest.app.managers.MyRunTestActionManager;
import com.technogym.equipmenttest.library.bridges.android.IEquipmentBridge;

public interface IMyRunEquipmentBridge extends IEquipmentBridge<MyRunTestActionManager> {

	@JavascriptInterface
	public void scanBarCode();

	@JavascriptInterface
	public boolean isBluetoothConnected();

	@JavascriptInterface
	public void toggleBluetooth();

	@JavascriptInterface
	public void askForEquipmentFirmwareVersion();

	@JavascriptInterface
	public void askForBootloaderFirmwareVersion();
}
