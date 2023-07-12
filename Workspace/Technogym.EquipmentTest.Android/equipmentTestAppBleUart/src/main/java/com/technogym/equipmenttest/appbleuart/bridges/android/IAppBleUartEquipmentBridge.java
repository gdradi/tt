package com.technogym.equipmenttest.appbleuart.bridges.android;

import android.webkit.JavascriptInterface;

import com.technogym.equipmenttest.library.bridges.android.IEquipmentBridge;
import com.technogym.equipmenttest.appbleuart.manager.BleUartActionManager;

public interface IAppBleUartEquipmentBridge extends IEquipmentBridge<BleUartActionManager> {

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

	@JavascriptInterface
	public void getHighKitVersion();

	@JavascriptInterface
	public void rebootEquipment(int pollingTime);

	@JavascriptInterface
	public void upgradeLowKitFwVersion(String modelCode, String widVersion, String token);

	@JavascriptInterface
	public void upgradeHighKitFwVersion(String modelCode, String widVersion, String token);

	@JavascriptInterface
	public void getWIDVersion();

	// region Live Essential
	@JavascriptInterface
	public void setTreadMillMachineType();

	@JavascriptInterface
	public void setSynchroMachineType();

	@JavascriptInterface
	public void setTopMachineType();

	@JavascriptInterface
	public void setBikeMachineType();

	@JavascriptInterface
	public void setReclineMachineType();

	@JavascriptInterface
	public void setClimbMachineType();

	@JavascriptInterface
	public void setVarioMachineType();

	@JavascriptInterface
	public void setWallPoweredType();

	@JavascriptInterface
	public void setSelfPoweredType();

	@JavascriptInterface
	public void getMachineType();

	@JavascriptInterface
	public void getPowerType();

	boolean sendBuzz();

	boolean sendKeyButton();

	boolean sendTextXScreen(String value);

	boolean sendTestScreenOff();

	boolean sendGetCardioricevitoreFw();

	boolean sendReadWifiConfig();

	boolean sendGetDate();

	boolean sendGetEmergency();

	boolean sendGetCardioHRValue();

	boolean sendStartCardioReceiver();

	boolean sendStopCardioReceiver();

	boolean sendGetSupercapStatus();

	boolean sendSetSpeed(String value);

	boolean sendSetGradient(String percentage);

	boolean sendKeyboardStatus();

	boolean sendReadInclinationTableCRC();
	// endregion
}
