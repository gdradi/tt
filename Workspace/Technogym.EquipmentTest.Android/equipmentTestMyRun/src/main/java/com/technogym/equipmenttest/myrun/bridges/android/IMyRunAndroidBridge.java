package com.technogym.equipmenttest.myrun.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.equipmenttest.library.bridges.android.IAndroidBridge;

public interface IMyRunAndroidBridge extends IAndroidBridge {

	@JavascriptInterface
	public void setUser(final String username);

	@JavascriptInterface
	public void setEquipmentSerialNumer(final String serialNumber);

	@JavascriptInterface
	public void executeNetworkBackupLog();

	@JavascriptInterface
	public void setEquipmentFirmwareVersion(final String equipmentVersion);
}
