package com.technogym.equipmenttest.myrun.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.equipmenttest.library.bridges.android.IEquipmentBridge;
import com.technogym.equipmenttest.myrun.manager.MyRunTestActionManager;

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

	@JavascriptInterface
	public void getLowKitVersion();
}
