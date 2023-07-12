package com.technogym.equipmenttest.appbleuart.bridges.web;

import com.technogym.equipmenttest.library.bridges.web.IJavascriptBridge;

public interface IAppBleUartJavascriptBridge extends IJavascriptBridge {

	/**
	 * This method notifies through the JavaScript interface when the low kit file was copied to the equipment flash memory
	 * @param result: success or not
	 */
	public void onBleUartLowKitUpgraded (boolean result);

	public void onBleUartHighKitUpgraded (boolean result);
	/**
	 * This method notifies through the JavaScript interface when the equipment is rebooted
	 * @param connected: if the equipment is connected
	 */
	public void onBleUartEquipmentRebooted (boolean connected);

	/**
	 * This method notifies through the JavaScript interface the firmware's low kit version
	 * @param version: the retrieved low kit version
	 */
    public void onLowKitVersionRetrieved(String version);

	/**
	 * This method notifies through the JavaScript interface the firmware's high kit version
	 * @param version: the retrieved low kit version
	 */
	public void onHighKitVersionRetrieved(String version);

    /**
	 * This method notifies through the JavaScript interface the firmware's bootloader version
	 * @param bootloaderVersion: the retrieved bootloader version
     */
	public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion);

	/**
	 * This method notifies through the JavaScript interface that the bluetooth connection changed its state.
	 * */
	public void onBluetoothConnectionStateChanged();

	/**
	 * This method notifies through the JavaScript interface the wid version
	 * @param version: the retrieved wid version
	 */
	public void onWIDVersionRetrieved(String version);

	// region Live Essential
	void onMachineTypeRetrieved(String machineType);

	void onPowerTypeRetrieved(String powerType);
	// endregion
}
