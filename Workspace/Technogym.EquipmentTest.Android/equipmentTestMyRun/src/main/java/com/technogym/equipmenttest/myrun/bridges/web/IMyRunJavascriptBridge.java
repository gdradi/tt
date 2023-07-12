package com.technogym.equipmenttest.myrun.bridges.web;

import com.technogym.equipmenttest.library.bridges.web.IJavascriptBridge;

public interface IMyRunJavascriptBridge extends IJavascriptBridge {

	/**
	 * This method notifies through the JavaScript interface the firmware's bootloader version
	 * @param bootloaderVersion: the retrieved bootloader version
     */
	public void onFirmwareBootloaderVersionRetrieved(String bootloaderVersion);

	/**
	 * This method notifies through the JavaScript interface that the bluetooth connection changed its state.
	 * */
	public void onBluetoothConnectionStateChanged();

	public void onLowKitVersionRetrieved(String bootloaderVersion);
}
